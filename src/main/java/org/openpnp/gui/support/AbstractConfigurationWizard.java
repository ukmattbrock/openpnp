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

package org.openpnp.gui.support;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.openpnp.gui.MainFrame;
import org.openpnp.model.Configuration;
import org.openpnp.util.BeanUtils;

public abstract class AbstractConfigurationWizard extends JPanel implements Wizard {
    protected WizardContainer wizardContainer;
    protected JPanel contentPanel;
    private JScrollPane scrollPane;

    public AbstractConfigurationWizard() {
        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(contentPanel);

        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    public abstract void createBindings();

    public void validateInput() throws Exception {

    }

    /**
     * This method should be called when the caller wishes to notify the user that there has been a
     * change to the state of the wizard. This is done automatically for wrapped bindings but this
     * method is provided for operations that do not use wrapped bindings.
     */
    protected void notifyChange() {
    }

    /**
     * When overriding this method you should call super.loadFromModel() AFTER doing any work that
     * you need to do, not before.
     */
    protected void loadFromModel() {
    }

    /**
     * When overriding this method you should call super.loadFromModel() AFTER doing any work that
     * you need to do, not before.
     */
    protected void saveToModel() {
        try {
            validateInput();
        }
        catch (Exception e) {
            MessageBoxes.errorBox(getTopLevelAncestor(), "Validation Error", e.getMessage());
        }
    }

    public void addWrappedBinding(Object source, String sourceProperty,
            Object target, String targetProperty, Converter converter) {
        AutoBinding binding = bind(UpdateStrategy.READ_WRITE, source, sourceProperty, target, targetProperty, converter);
        binding.addBindingListener(new PropertyEditBindingListener(source, sourceProperty));
    }

    public void addWrappedBinding(Object source, String sourceProperty,
            Object target, String targetProperty) {
        addWrappedBinding(source, sourceProperty, target, targetProperty, null);
    }

    public AutoBinding bind(UpdateStrategy updateStrategy, Object source, String sourceProperty,
            Object target, String targetProperty) {
        return bind(updateStrategy, source, sourceProperty, target, targetProperty, null);
    }

    public AutoBinding bind(UpdateStrategy updateStrategy, Object source, String sourceProperty,
            Object target, String targetProperty, Converter converter) {
        AutoBinding binding = BeanUtils.bind(updateStrategy, source, sourceProperty, target, targetProperty, converter);
        if (target instanceof JComponent) {
            binding.addBindingListener(new JComponentBackgroundUpdater((JComponent) target));
        }
        return binding;
    }

    @Override
    public void setWizardContainer(WizardContainer wizardContainer) {
        this.wizardContainer = wizardContainer;
        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(Configuration.get().getVerticalScrollUnitIncrement());
        createBindings();
        loadFromModel();
    }

    @Override
    public JPanel getWizardPanel() {
        return this;
    }

    @Override
    public String getWizardName() {
        return null;
    }
    
    private static class PropertyEditBindingListener extends AbstractBindingListener {
        final Object source;
        final String sourceProperty;
        
        public PropertyEditBindingListener(Object source, String sourceProperty) {
            this.source = source;
            this.sourceProperty = sourceProperty;
        }
        
        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            System.out.println("targetChanged " + sourceProperty);
            MainFrame.get().getUndoManager().addEdit(new PropertyEdit(source, sourceProperty, event.getNewValue()));
        }
    }
    
    private static class JComponentBackgroundUpdater extends AbstractBindingListener {
        private static Color errorColor = new Color(0xff, 0xdd, 0xdd);
        private JComponent component;
        private Color oldBackground;

        public JComponentBackgroundUpdater(JComponent component) {
            this.component = component;
            oldBackground = component.getBackground();
        }

        @Override
        public void syncFailed(Binding binding, SyncFailure failure) {
            component.setBackground(errorColor);
        }

        @Override
        public void synced(Binding binding) {
            component.setBackground(oldBackground);
        }
    }
}
