package org.openpnp.machine.reference.driver.wizards.gcode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.openpnp.gui.components.ComponentDecorators;
import org.openpnp.gui.support.AbstractConfigurationWizard;
import org.openpnp.gui.support.DoubleConverter;
import org.openpnp.machine.reference.driver.GcodeDriver;
import org.openpnp.machine.reference.driver.GcodeDriver.Axis;
import org.openpnp.machine.reference.driver.GcodeDriver.AxisTransform;
import org.openpnp.machine.reference.driver.GcodeDriver.CamTransform;
import org.openpnp.machine.reference.driver.GcodeDriver.NegatingTransform;
import org.openpnp.machine.reference.driver.wizards.gcode.GcodeDriverGcodes.HeadMountableItem;
import org.openpnp.model.AbstractModelObject;
import org.openpnp.model.Configuration;
import org.openpnp.spi.Actuator;
import org.openpnp.spi.Camera;
import org.openpnp.spi.Head;
import org.openpnp.spi.HeadMountable;
import org.openpnp.spi.Nozzle;
import org.openpnp.spi.PasteDispenser;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class GcodeDriverAxes extends AbstractConfigurationWizard {
    private final GcodeDriver driver;
    private Proxies proxies = new Proxies();
    
    public GcodeDriverAxes(GcodeDriver driver) {
        this.driver = driver;
        
        JPanel panel = new JPanel();
        contentPanel.add(panel);
        panel.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        lblWarningApplyAnd = new JLabel("Warning: Apply and Reset do not yet work in this panel.");
        lblWarningApplyAnd.setForeground(Color.RED);
        panel.add(lblWarningApplyAnd, "2, 2, 7, 1");
        
        lblYourChangesWill = new JLabel("Your changes will take effect immediately.");
        lblYourChangesWill.setForeground(Color.RED);
        panel.add(lblYourChangesWill, "2, 4, 7, 1");
        
        JLabel lblAxis = new JLabel("Axis");
        panel.add(lblAxis, "2, 6, right, default");
        
        axisCb = new JComboBox();
        panel.add(axisCb, "4, 6");
        
        btnAddNew = new JButton("Add New");
        panel.add(btnAddNew, "6, 6");
        
        btnDeleteSelected = new JButton("Delete Selected");
        panel.add(btnDeleteSelected, "8, 6");
        
        JLabel lblName = new JLabel("Name");
        panel.add(lblName, "2, 8, right, default");
        
        nameTf = new JTextField();
        panel.add(nameTf, "4, 8");
        nameTf.setColumns(10);
        
        JLabel lblType = new JLabel("Type");
        panel.add(lblType, "2, 10, right, default");
        
        typeCb = new JComboBox();
        panel.add(typeCb, "4, 10");
        
        JLabel lblHomeCoordinate = new JLabel("Home Coordinate");
        panel.add(lblHomeCoordinate, "2, 12, right, default");
        
        homeCoordTf = new JTextField();
        panel.add(homeCoordTf, "4, 12");
        homeCoordTf.setColumns(10);
        
        JLabel lblHeadMountables = new JLabel("Head Mountables");
        panel.add(lblHeadMountables, "2, 16, right, default");
        
        headMountableCb = new JComboBox();
        panel.add(headMountableCb, "4, 16");
        
        headMountableAttachedChk = new JCheckBox("Attached");
        panel.add(headMountableAttachedChk, "6, 16");
        
        lblPremoveCommand = new JLabel("Pre-Move Command");
        panel.add(lblPremoveCommand, "2, 14, right, default");
        
        preMoveTf = new JTextField();
        panel.add(preMoveTf, "4, 14, fill, default");
        preMoveTf.setColumns(10);
        
        JLabel lblTransform = new JLabel("Transform");
        panel.add(lblTransform, "2, 18, right, default");
        
        transformCb = new JComboBox();
        panel.add(transformCb, "4, 18");
        
        fillHeadMountables();
        fillAxes();
        fillTypes();
        fillTransforms();
    }
    
    private void fillTransforms() {
        transformCb.addItem(new ClassItem(null));
        transformCb.addItem(new ClassItem(CamTransform.class));
        transformCb.addItem(new ClassItem(NegatingTransform.class));
    }
    
    private void fillTypes() {
        for (Axis.Type type : Axis.Type.values()) {
            typeCb.addItem(type);
        }
    }
    
    private void fillAxes() {
        for (Axis axis : driver.getAxes()) {
            axisCb.addItem(new AxisItem(axis));
        }
    }
    
    private static List<HeadMountable> getHeadMountables() {
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
    
    private void fillHeadMountables() {
        for (HeadMountable hm : getHeadMountables()) {
            headMountableCb.addItem(new HeadMountableItem(hm));
        }
    }
    
    public Axis getSelectedAxis() {
        return ((AxisItem) axisCb.getSelectedItem()).axis;
    }
    
    @Override
    public void createBindings() {
        // TODO: Selecing a new axis doesn't load the head mountable attached properly.
        // Same with the transforms.
        DoubleConverter doubleConverter = new DoubleConverter(Configuration.get().getLengthDisplayFormat());
        
        bind(UpdateStrategy.READ_WRITE, axisCb, "selectedItem.axis.name", nameTf, "text");
        bind(UpdateStrategy.READ_WRITE, axisCb, "selectedItem.axis.type", typeCb, "selectedItem");
        bind(UpdateStrategy.READ_WRITE, axisCb, "selectedItem.axis.homeCoordinate", homeCoordTf, "text", doubleConverter);
        bind(UpdateStrategy.READ_WRITE, axisCb, "selectedItem.axis.preMoveCommand", preMoveTf, "text");
        bind(UpdateStrategy.READ, axisCb, "selectedItem.axis", proxies, "selectedAxis");
        bind(UpdateStrategy.READ_WRITE, proxies, "selectedHeadMountable", headMountableCb, "selectedItem.headMountable");
        bind(UpdateStrategy.READ_WRITE, proxies, "headMountableAttached", headMountableAttachedChk, "selected");
        bind(UpdateStrategy.READ_WRITE, proxies, "transformClass", transformCb, "selectedItem.cls");
        
        ComponentDecorators.decorateWithAutoSelect(homeCoordTf);
        ComponentDecorators.decorateWithAutoSelect(preMoveTf);
    }
    
    public static class AxisItem {
        public final Axis axis;
        
        public AxisItem(Axis axis) {
            this.axis = axis;
        }
        
        public Axis getAxis() {
            return axis;
        }
        
        public String toString() {
            return String.format("%s (%s)", axis.getName(), axis.getType().toString());
        }
    }
    
    public static class ClassItem {
        public final Class cls;
        
        public ClassItem(Class cls) {
            this.cls = cls;
        }
        
        public Class getCls() {
            return cls;
        }
        
        public String toString() {
            return cls == null ? "None" : cls.getSimpleName();
        }
    }
    
    public class Proxies extends AbstractModelObject {
        private HeadMountable selectedHeadMountable;
        
        public void setSelectedAxis(Axis axis) {
            firePropertyChange("selectedHeadMountable", null, getSelectedHeadMountable());
            firePropertyChange("headMountableAttached", null, isHeadMountableAttached());
            firePropertyChange("transformClass", null, getTransformClass());
        }

        public HeadMountable getSelectedHeadMountable() {
            return selectedHeadMountable;
        }

        public void setSelectedHeadMountable(HeadMountable selectedHeadMountable) {
            Object oldValue = this.selectedHeadMountable;
            this.selectedHeadMountable = selectedHeadMountable;
            firePropertyChange("selectedHeadMountable", oldValue, selectedHeadMountable);
            firePropertyChange("headMountableAttached", null, isHeadMountableAttached());
            firePropertyChange("transformClass", null, getTransformClass());
        }
        
        private void cleanupHeadMountables() {
            Axis axis = getSelectedAxis();
            if (axis.getHeadMountableIds().contains("*")) {
                axis.getHeadMountableIds().remove("*");
                for (HeadMountable hm : getHeadMountables()) {
                    axis.getHeadMountableIds().add(hm.getId());
                }
            }
        }
        
        public void setHeadMountableAttached(boolean attached) {
            cleanupHeadMountables();
            Axis axis = getSelectedAxis();
            if (getSelectedHeadMountable() == null) {
                return;
            }
            HeadMountable hm = getSelectedHeadMountable();
            if (attached) {
                axis.getHeadMountableIds().add(hm.getId());
            }
            else {
                axis.getHeadMountableIds().remove(hm.getId());
            }
        }
        
        public boolean isHeadMountableAttached() {
            Axis axis = getSelectedAxis();
            if (getSelectedHeadMountable() == null) {
                return false;
            }
            HeadMountable hm = getSelectedHeadMountable();
            if (axis.getHeadMountableIds().contains("*") || axis.getHeadMountableIds().contains(hm.getId())) {
                return true;
            }
            return false;
        }
        
        public Class getTransformClass() {
            System.out.println("getTransformClass");
            Axis axis = getSelectedAxis();
            if (axis.getTransform() == null) {
                return null;
            }
            return axis.getTransform().getClass();
        }
        
        public void setTransformClass(Class cls) throws Exception {
            Axis axis = getSelectedAxis();
            // Don't allow the binding to re-set the class when it's just setting
            // the same thing as this would wipe out the config.
            if (axis.getTransform() != null && axis.getTransform().getClass() == cls) {
                return;
            }
            System.out.println("setTransformClass " + cls);
            if (cls == null) {
                axis.setTransform(null);
                return;
            }
            AxisTransform transform = (AxisTransform) cls.newInstance();
            axis.setTransform(transform);
        }
    }

    private JTextField nameTf;
    private JTextField homeCoordTf;
    private JButton btnAddNew;
    private JButton btnDeleteSelected;
    private JComboBox axisCb;
    private JComboBox typeCb;
    private JComboBox headMountableCb;
    private JComboBox transformCb;
    private JCheckBox headMountableAttachedChk;
    private JLabel lblPremoveCommand;
    private JTextField preMoveTf;
    private JLabel lblWarningApplyAnd;
    private JLabel lblYourChangesWill;
}
