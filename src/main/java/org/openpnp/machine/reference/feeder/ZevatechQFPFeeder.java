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
 * For more information 
 /*
QFP Handler

Rotates the QFP part for the QFP head
 */
package org.openpnp.machine.reference.feeder;

import javax.swing.Action;

import org.openpnp.gui.support.Wizard;
import org.openpnp.machine.reference.ReferenceFeeder;
import org.openpnp.machine.reference.feeder.wizards.ReferenceFeederConfigurationWizard;
import org.openpnp.model.Location;
import org.openpnp.spi.Nozzle;
import org.openpnp.spi.PropertySheetHolder;

/**
 * Zevatech QFP feeder
 */
public class ZevatechQFPFeeder extends ReferenceFeeder {


    @Override
    public Location getPickLocation() throws Exception {
        return location;
    }
    @Override
    public void feed(Nozzle nozzle) throws Exception {
        
        // we can only feed from the special QFP nozzle!
        if(!nozzle.getName().equals("QFPNozzle"))
        {
            throw new Exception("This feeder will only work with the QFPNozzle!");
        }

        // rotate the QFP handler to our placement angle
    }

    @Override
    public Wizard getConfigurationWizard() {
        return new ReferenceFeederConfigurationWizard(this);
    }

    @Override
    public String getPropertySheetHolderTitle() {
        return getClass().getSimpleName() + " " + getName();
    }

    @Override
    public PropertySheetHolder[] getChildPropertySheetHolders() {
        return null;
    }
    @Override
    public Action[] getPropertySheetHolderActions() {
        return null;
    }
}
