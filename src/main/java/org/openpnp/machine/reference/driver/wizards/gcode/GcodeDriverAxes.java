package org.openpnp.machine.reference.driver.wizards.gcode;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
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
import org.openpnp.machine.reference.driver.wizards.gcode.GcodeDriverAxesVm.AxisWrapper;
import org.openpnp.model.Configuration;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class GcodeDriverAxes extends AbstractConfigurationWizard {
    private final GcodeDriver driver;
    private final GcodeDriverAxesVm vm;
    
    public GcodeDriverAxes(GcodeDriver driver) {
        this.driver = driver;
        this.vm = new GcodeDriverAxesVm(driver);
        
        createUi();
    }
    
    @Override
    public void createBindings() {
        DoubleConverter doubleConverter = new DoubleConverter(Configuration.get().getLengthDisplayFormat());
        
        createAxis.addActionListener(e -> vm.createAxis());
        
        deleteAxis.addActionListener(e -> vm.deleteSelectedAxis());
        
        vm.addPropertyChangeListener("axes", e -> 
            axis.setModel(new DefaultComboBoxModel<>(vm.getAxes().toArray(new AxisWrapper[]{}))));
        
        bind(vm, "axis", axis, "selectedItem");
        bind(vm, "axis.name", name, "text");
        bind(vm, "axis.homeCoordinate", homeCoordinate, "text", doubleConverter);

        ComponentDecorators.decorateWithAutoSelect(homeCoordinate);
        ComponentDecorators.decorateWithAutoSelect(preMoveCommand);
    }
    
    private void createUi() {
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
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        JLabel lblAxis = new JLabel("Axis");
        panel.add(lblAxis, "2, 2, right, default");
        
        axis = new JComboBox<>();
        panel.add(axis, "4, 2");
        
        createAxis = new JButton("Create New Axis");
        panel.add(createAxis, "6, 2");
        
        deleteAxis = new JButton("Delete Selected Axis");
        panel.add(deleteAxis, "8, 2");
        
        JLabel lblName = new JLabel("Name");
        panel.add(lblName, "2, 4, right, default");
        
        name = new JTextField();
        panel.add(name, "4, 4");
        name.setColumns(10);
        
        JLabel lblType = new JLabel("Type");
        panel.add(lblType, "2, 6, right, default");
        
        type = new JComboBox<>();
        panel.add(type, "4, 6");
        
        JLabel lblHomeCoordinate = new JLabel("Home Coordinate");
        panel.add(lblHomeCoordinate, "2, 8, right, default");
        
        homeCoordinate = new JTextField();
        panel.add(homeCoordinate, "4, 8");
        homeCoordinate.setColumns(10);
        
        JLabel lblHeadMountables = new JLabel("Head Mountables");
        panel.add(lblHeadMountables, "2, 12, right, default");
        
        headMountable = new JComboBox();
        panel.add(headMountable, "4, 12");
        
        headMountableAttached = new JCheckBox("Attached");
        panel.add(headMountableAttached, "6, 12");
        
        lblPremoveCommand = new JLabel("Pre-Move Command");
        panel.add(lblPremoveCommand, "2, 10, right, default");
        
        preMoveCommand = new JTextField();
        panel.add(preMoveCommand, "4, 10, fill, default");
        preMoveCommand.setColumns(10);
        
        JLabel lblTransform = new JLabel("Transform");
        panel.add(lblTransform, "2, 14, right, default");
        
        transform = new JComboBox();
        panel.add(transform, "4, 14");
        
        transformPanel = new JPanel();
        contentPanel.add(transformPanel);
        transformPanel.setLayout(new BorderLayout(0, 0));
    }
    
    private JTextField name;
    private JTextField homeCoordinate;
    private JButton createAxis;
    private JButton deleteAxis;
    private JComboBox<AxisWrapper> axis;
    private JComboBox<Axis.Type> type;
    private JComboBox headMountable;
    private JComboBox transform;
    private JCheckBox headMountableAttached;
    private JLabel lblPremoveCommand;
    private JTextField preMoveCommand;
    private JPanel transformPanel;
}
