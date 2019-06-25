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

package org.openpnp.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractModelObject {
    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    protected void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
        propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
    }
    
    public static class TestA extends AbstractModelObject {
        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize " + getClass());
        }
    }
    
    public static class TestB extends AbstractModelObject {
        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
        
        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize " + getClass());
        }
    }
    
    public static void main(String[] args) throws Exception {
        TestA model = new TestA();
        TestB ui = new TestB();
        /**
         * So, a typical example of concern is we have a model object that is long lived, and
         * we have a wizard with fields that is short lived.
         * 
         * If we bind model.name to ui.name the following happens:
         * 1. a property change listener is added to the model: pcl_model
         * 2. a different property change listener is added to the ui: pcl_ui
         * 
         * So the model has pcl_model, and pcl_model probably has model.
         * 
         * And likewise, ui has pcl_ui, and pcl_ui has ui.
         * 
         * And somehow they all have to reference each other so that they can actually make the change.
         */
//        BeanUtils.bind(UpdateStrategy.READ_WRITE, model, "str", ui, "str", null);
//        model = null;
        ui = null;
        
        while (true) {
            System.gc();
            Thread.sleep(1000);
        }
    }
}
