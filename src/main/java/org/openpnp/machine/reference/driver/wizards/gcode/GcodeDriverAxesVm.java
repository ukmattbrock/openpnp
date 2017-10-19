package org.openpnp.machine.reference.driver.wizards.gcode;

import java.util.ArrayList;
import java.util.List;

import org.openpnp.machine.reference.driver.GcodeDriver;
import org.openpnp.machine.reference.driver.GcodeDriver.Axis;
import org.openpnp.model.AbstractModelObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GcodeDriverAxesVm extends AbstractModelObject {
    private final GcodeDriver driver;
    
    private List<AxisWrapper> axes;
    private AxisWrapper axis;
    private String name;
    private Axis.Type type;
    
    public GcodeDriverAxesVm(GcodeDriver driver) {
        this.driver = driver;
        this.axes = new ArrayList<>();
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
    
//    public static List<HeadMountable> getHeadMountables() {
//        List<HeadMountable> headMountables = new ArrayList<HeadMountable>();
//        for (Head head : Configuration.get().getMachine().getHeads()) {
//            for (Nozzle hm : head.getNozzles()) {
//                headMountables.add(hm);
//            }
//            for (PasteDispenser hm : head.getPasteDispensers()) {
//                headMountables.add(hm);
//            }
//            for (Camera hm : head.getCameras()) {
//                headMountables.add(hm);
//            }
//            for (Actuator hm : head.getActuators()) {
//                headMountables.add(hm);
//            }
//        }
//        for (Actuator hm : Configuration.get().getMachine().getActuators()) {
//            headMountables.add(hm);
//        }
//        return headMountables;
//    }
//    
//    public List<ClassItem> getTransforms() {
//        List<ClassItem> transforms = new ArrayList<ClassItem>();
//        transforms.add(new ClassItem(null));
//        transforms.add(new ClassItem(CamTransform.class));
//        transforms.add(new ClassItem(NegatingTransform.class));
//    }
//
//    private void fillTypes() {
//        for (Axis.Type type : Axis.Type.values()) {
//            typeCb.addItem(type);
//        }
//    }
//    
//    private void fillAxes() {
//        for (Axis axis : driver.getAxes()) {
//            axisCb.addItem(new AxisItem(axis));
//        }
//    }
    
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
