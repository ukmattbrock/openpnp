/*
 * Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 * 
 * This file is part of OpenPnP.
 * 
 * OpenPnP is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenPnP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with OpenPnP. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.machine.reference.wizards;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.openpnp.gui.components.ComponentDecorators;
import org.openpnp.gui.support.AbstractConfigurationWizard;
import org.openpnp.gui.support.DoubleConverter;
import org.openpnp.gui.support.IntegerConverter;
import org.openpnp.gui.support.LengthConverter;
import org.openpnp.gui.support.MutableLocationProxy;
import org.openpnp.machine.reference.ReferenceActuator;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class ReferenceActuatorConfigurationWizard extends AbstractConfigurationWizard {
    private final ReferenceActuator actuator;

    private JTextField locationX;
    private JTextField locationY;
    private JTextField locationZ;
    private JPanel panelOffsets;
    private JPanel panelSafeZ;
    private JLabel lblSafeZ;
    private JTextField textFieldSafeZ;
    private JPanel headMountablePanel;
    private JPanel generalPanel;
    private JLabel lblIndex;
    private JTextField indexTextField;
    private JPanel panelProperties;
    private JLabel lblName;
    private JTextField nameTf;
    private JPanel panelExtents;
    private JLabel lblNewLabel;
    private JLabel lbl5;
    private JLabel lbl6;
    private JTextField valueMinimum;
    private JTextField valueMaximum;
    private JTextField valueStep;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;

    public ReferenceActuatorConfigurationWizard(ReferenceActuator actuator) {
        this.actuator = actuator;
        createUi();
        if (actuator.getHead() == null) {
            headMountablePanel.setVisible(false);
        }
    }
    private void createUi() {
        
        panelProperties = new JPanel();
        panelProperties.setBorder(new TitledBorder(null, "Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panelProperties);
        panelProperties.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        lblName = new JLabel("Name");
        panelProperties.add(lblName, "2, 2, right, default");
        
        nameTf = new JTextField();
        panelProperties.add(nameTf, "4, 2, fill, default");
        nameTf.setColumns(20);
        
                headMountablePanel = new JPanel();
        headMountablePanel.setLayout(new BoxLayout(headMountablePanel, BoxLayout.Y_AXIS));
        contentPanel.add(headMountablePanel);
        
                panelOffsets = new JPanel();
        headMountablePanel.add(panelOffsets);
        panelOffsets.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                "Offsets", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelOffsets.setLayout(new FormLayout(
                new ColumnSpec[] {FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
                        FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
                        FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
                        FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,},
                new RowSpec[] {FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,}));
        
                JLabel lblX = new JLabel("X");
        panelOffsets.add(lblX, "2, 2");
        
                JLabel lblY = new JLabel("Y");
        panelOffsets.add(lblY, "4, 2");
        
                JLabel lblZ = new JLabel("Z");
        panelOffsets.add(lblZ, "6, 2");
        
                locationX = new JTextField();
        panelOffsets.add(locationX, "2, 4");
        locationX.setColumns(5);
        
                locationY = new JTextField();
        panelOffsets.add(locationY, "4, 4");
        locationY.setColumns(5);
        
                locationZ = new JTextField();
        panelOffsets.add(locationZ, "6, 4");
        locationZ.setColumns(5);
        
                panelSafeZ = new JPanel();
        headMountablePanel.add(panelSafeZ);
        panelSafeZ.setBorder(new TitledBorder(null, "Safe Z", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        panelSafeZ.setLayout(new FormLayout(
                new ColumnSpec[] {FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
                        FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,},
                new RowSpec[] {FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,}));
        
                lblSafeZ = new JLabel("Safe Z");
        panelSafeZ.add(lblSafeZ, "2, 2, right, default");
        
                textFieldSafeZ = new JTextField();
        panelSafeZ.add(textFieldSafeZ, "4, 2, fill, default");
        textFieldSafeZ.setColumns(10);
        
        generalPanel = new JPanel();
        generalPanel.setBorder(new TitledBorder(null, "General", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(generalPanel);
        generalPanel.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        lblIndex = new JLabel("Index");
        generalPanel.add(lblIndex, "2, 2, right, default");
        
        indexTextField = new JTextField();
        generalPanel.add(indexTextField, "4, 2, fill, default");
        indexTextField.setColumns(10);
        
        panelExtents = new JPanel();
        panelExtents.setBorder(new TitledBorder(null, "Value Extents", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panelExtents);
        panelExtents.setLayout(new FormLayout(new ColumnSpec[] {
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
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        lblNewLabel = new JLabel("Value Minimum");
        panelExtents.add(lblNewLabel, "2, 2, right, default");
        
        valueMinimum = new JTextField();
        panelExtents.add(valueMinimum, "4, 2");
        valueMinimum.setColumns(10);
        
        lblNewLabel_1 = new JLabel("Minimum value allowed when choosing a value.");
        panelExtents.add(lblNewLabel_1, "6, 2");
        
        lbl5 = new JLabel("Value Maximum");
        panelExtents.add(lbl5, "2, 4, right, default");
        
        valueMaximum = new JTextField();
        panelExtents.add(valueMaximum, "4, 4");
        valueMaximum.setColumns(10);
        
        lblNewLabel_2 = new JLabel("Maximum value allowed when choosing a value.");
        panelExtents.add(lblNewLabel_2, "6, 4");
        
        lbl6 = new JLabel("Value Step");
        panelExtents.add(lbl6, "2, 6, right, default");
        
        valueStep = new JTextField();
        panelExtents.add(valueStep, "4, 6");
        valueStep.setColumns(10);
        
        lblNewLabel_3 = new JLabel("Stepping value used when displaying a slider.");
        panelExtents.add(lblNewLabel_3, "6, 6");
    }

    @Override
    public void createBindings() {
        LengthConverter lengthConverter = new LengthConverter();
        IntegerConverter intConverter = new IntegerConverter();
        DoubleConverter doubleConverter = new DoubleConverter("%f");

        addWrappedBinding(actuator, "name", nameTf, "text");
        MutableLocationProxy headOffsets = new MutableLocationProxy();
        bind(UpdateStrategy.READ_WRITE, actuator, "headOffsets", headOffsets, "location");
        addWrappedBinding(headOffsets, "lengthX", locationX, "text", lengthConverter);
        addWrappedBinding(headOffsets, "lengthY", locationY, "text", lengthConverter);
        addWrappedBinding(headOffsets, "lengthZ", locationZ, "text", lengthConverter);
        addWrappedBinding(actuator, "safeZ", textFieldSafeZ, "text", lengthConverter);
        addWrappedBinding(actuator, "index", indexTextField, "text", intConverter);
        addWrappedBinding(actuator, "doubleValueMinimum", valueMinimum, "text", doubleConverter);
        addWrappedBinding(actuator, "doubleValueMaximum", valueMaximum, "text", doubleConverter);
        addWrappedBinding(actuator, "doubleValueStep", valueStep, "text", doubleConverter);

        ComponentDecorators.decorateWithAutoSelect(nameTf);
        ComponentDecorators.decorateWithAutoSelect(indexTextField);
        ComponentDecorators.decorateWithAutoSelectAndLengthConversion(locationX);
        ComponentDecorators.decorateWithAutoSelectAndLengthConversion(locationY);
        ComponentDecorators.decorateWithAutoSelectAndLengthConversion(locationZ);
        ComponentDecorators.decorateWithAutoSelectAndLengthConversion(textFieldSafeZ);
        ComponentDecorators.decorateWithAutoSelect(valueMinimum);
        ComponentDecorators.decorateWithAutoSelect(valueMaximum);
        ComponentDecorators.decorateWithAutoSelect(valueStep);
    }
}
