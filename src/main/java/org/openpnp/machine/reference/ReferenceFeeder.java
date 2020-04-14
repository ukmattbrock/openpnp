package org.openpnp.machine.reference;

import java.util.List;

import org.openpnp.model.LengthUnit;
import org.openpnp.model.Location;
import org.openpnp.spi.Feeder;
import org.openpnp.spi.base.AbstractFeeder;
import org.openpnp.spi.Nozzle;
import org.simpleframework.xml.Element;

public abstract class ReferenceFeeder extends AbstractFeeder {
    @Element
    protected Location location = new Location(LengthUnit.Millimeters);

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        Object oldValue = this.location;
        this.location = location;
        firePropertyChange("location", oldValue, location);
    }

    @Override
    public void prepareForJob(List<Feeder> feedersToPrepare) throws Exception {
        // the default RefrenceFeeder needs no prep.
    }
}
