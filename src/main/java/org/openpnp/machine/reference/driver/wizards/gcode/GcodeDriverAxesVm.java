package org.openpnp.machine.reference.driver.wizards.gcode;

import java.util.ArrayList;
import java.util.List;

import org.openpnp.machine.reference.driver.GcodeDriver;
import org.openpnp.machine.reference.driver.GcodeDriver.Axis;
import org.openpnp.machine.reference.driver.GcodeDriver.CamTransform;
import org.openpnp.machine.reference.driver.GcodeDriver.NegatingTransform;
import org.openpnp.model.AbstractModelObject;
import org.openpnp.model.Configuration;
import org.openpnp.spi.Actuator;
import org.openpnp.spi.Camera;
import org.openpnp.spi.Head;
import org.openpnp.spi.HeadMountable;
import org.openpnp.spi.Nozzle;
import org.openpnp.spi.PasteDispenser;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GcodeDriverAxesVm extends AbstractModelObject {
    private final GcodeDriver driver;
    
    private List<AxisWrapper> axes;
    private AxisWrapper axis;
    
    public GcodeDriverAxesVm(GcodeDriver driver) {
        this.driver = driver;
        this.axes = new ArrayList<>();
    }
    
    public void setAxis(AxisWrapper axis) {
        this.axis = axis;
        firePropertyChange("axis", null, getAxis());
    }
    
    public void createAxis() {
        AxisWrapper axis = new AxisWrapper();
        axis.setName("New Axis");
        axis.setType(Axis.Type.X);
        axes.add(axis);
        firePropertyChange("axes", null, axes);
    }
    
    public void deleteSelectedAxis() {
        axes.remove(axis);
        firePropertyChange("axes", null, axes);
    }
    
    public static List<HeadMountable> getHeadMountables() {
        List<HeadMountable> headMountables = new ArrayList<HeadMountable>();
        for (Head head : Configuration.get().getMachine().getHeads()) {
            for (Nozzle hm : head.getNozzles()) {
                headMountables.add(hm);
            }
            for (PasteDispenser hm : head.getPasteDispensers()) {
                headMountables.add(hm);
            }
            for (Camera hm : head.getCameras()) {
                headMountables.add(hm);
            }
            for (Actuator hm : head.getActuators()) {
                headMountables.add(hm);
            }
        }
        for (Actuator hm : Configuration.get().getMachine().getActuators()) {
            headMountables.add(hm);
        }
        return headMountables;
    }
    
    public List<ClassItem> getTransforms() {
        List<ClassItem> transforms = new ArrayList<ClassItem>();
        transforms.add(new ClassItem(null));
        transforms.add(new ClassItem(CamTransform.class));
        transforms.add(new ClassItem(NegatingTransform.class));
        return transforms;
    }
    
    // TODO STOPSHIP this kinda mixes view code with viewmodel code. Try to find a better
    // way to handle it. Same with the ClassItem below.
    public static class AxisWrapper extends Axis {
        public String toString() {
            return String.format("%s (%s)", getName(), getType());
        }
    }
    
    @Data
    public static class ClassItem {
        public final Class<?> cls;
                
        public String toString() {
            return cls == null ? "None" : cls.getSimpleName();
        }
    }    
}
