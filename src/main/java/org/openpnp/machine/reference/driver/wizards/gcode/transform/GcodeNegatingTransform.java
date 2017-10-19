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
import org.openpnp.machine.reference.driver.GcodeDriver.NegatingTransform;
import org.openpnp.machine.reference.driver.wizards.gcode.GcodeDriverAxes;
import org.openpnp.machine.reference.driver.wizards.gcode.GcodeDriverGcodes.HeadMountableItem;
import org.openpnp.model.Configuration;
import org.openpnp.spi.HeadMountable;
import org.openpnp.util.BeanUtils;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class GcodeNegatingTransform extends JPanel {
    final NegatingTransform transform;
    private JComboBox negatedHmCb;
    
    public GcodeNegatingTransform(NegatingTransform transform) {
        setBorder(new TitledBorder(null, "Negating Transform", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.transform = transform;
        setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        JLabel lblNegatedHeadMountable = new JLabel("Negated Head Mountable");
        add(lblNegatedHeadMountable, "2, 2, right, default");
        
        negatedHmCb = new JComboBox();
        add(negatedHmCb, "4, 2");
        
//        for (HeadMountable hm : GcodeDriverAxes.getHeadMountables()) {
//            negatedHmCb.addItem(new HeadMountableItem(hm));
//        }
        
        DoubleConverter doubleConverter = new DoubleConverter(Configuration.get().getLengthDisplayFormat());
        BeanUtils.bind(UpdateStrategy.READ_WRITE, transform, "negatedHeadMountableId", negatedHmCb, "selectedItem.headMountable.id");
    }
}
