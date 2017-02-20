package org.openpnp.machine.reference.driver.wizards.gcode.transform;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.openpnp.gui.components.ComponentDecorators;
import org.openpnp.gui.support.DoubleConverter;
import org.openpnp.gui.support.JBindings;
import org.openpnp.machine.reference.driver.GcodeDriver.CamTransform;
import org.openpnp.machine.reference.driver.wizards.gcode.GcodeDriverAxes;
import org.openpnp.machine.reference.driver.wizards.gcode.GcodeDriverGcodes.HeadMountableItem;
import org.openpnp.model.Configuration;
import org.openpnp.spi.HeadMountable;
import org.openpnp.util.BeanUtils;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class GcodeCamTransform extends JPanel {
    final CamTransform transform;
    private JTextField camRadiusTf;
    private JTextField camWheelRadiusTf;
    private JTextField camWheelGapTf;
    private JComboBox negatedHmCb;
    
    public GcodeCamTransform(CamTransform transform) {
        setBorder(new TitledBorder(null, "Cam Transform", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.transform = transform;
        setLayout(new FormLayout(new ColumnSpec[] {
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
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        JLabel lblNegatedHeadMountable = new JLabel("Negated Head Mountable");
        add(lblNegatedHeadMountable, "2, 2, right, default");
        
        negatedHmCb = new JComboBox();
        add(negatedHmCb, "4, 2");
        
        JLabel lblCamRadius = new JLabel("Cam Radius");
        add(lblCamRadius, "2, 4, right, default");
        
        camRadiusTf = new JTextField();
        add(camRadiusTf, "4, 4");
        camRadiusTf.setColumns(10);
        
        JLabel lblCamWheelRadius = new JLabel("Cam Wheel Radius");
        add(lblCamWheelRadius, "2, 6, right, default");
        
        camWheelRadiusTf = new JTextField();
        add(camWheelRadiusTf, "4, 6");
        camWheelRadiusTf.setColumns(10);
        
        JLabel lblCamWheelGap = new JLabel("Cam Wheel Gap");
        add(lblCamWheelGap, "2, 8, right, default");
        
        camWheelGapTf = new JTextField();
        add(camWheelGapTf, "4, 8");
        camWheelGapTf.setColumns(10);

        for (HeadMountable hm : GcodeDriverAxes.getHeadMountables()) {
            negatedHmCb.addItem(new HeadMountableItem(hm));
        }
        
        DoubleConverter doubleConverter = new DoubleConverter(Configuration.get().getLengthDisplayFormat());
        BeanUtils.bind(UpdateStrategy.READ_WRITE, transform, "negatedHeadMountableId", negatedHmCb, "selectedItem.headMountable.id");
        BeanUtils.bind(UpdateStrategy.READ_WRITE, transform, "camRadius", camRadiusTf, "text", doubleConverter);
        BeanUtils.bind(UpdateStrategy.READ_WRITE, transform, "camWheelRadius", camWheelRadiusTf, "text", doubleConverter);
        BeanUtils.bind(UpdateStrategy.READ_WRITE, transform, "camWheelGap", camWheelGapTf, "text", doubleConverter);
        
        ComponentDecorators.decorateWithAutoSelect(camRadiusTf);
        ComponentDecorators.decorateWithAutoSelect(camWheelRadiusTf);
        ComponentDecorators.decorateWithAutoSelect(camWheelGapTf);
    }
}
