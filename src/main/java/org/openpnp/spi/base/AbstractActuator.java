package org.openpnp.spi.base;

import javax.swing.Icon;

import org.openpnp.model.AbstractModelObject;
import org.openpnp.model.Configuration;
import org.openpnp.model.Location;
import org.openpnp.spi.Actuator;
import org.openpnp.spi.Camera;
import org.openpnp.spi.Head;
import org.simpleframework.xml.Attribute;

public abstract class AbstractActuator extends AbstractModelObject implements Actuator {
    @Attribute
    protected String id;

    @Attribute(required = false)
    protected String name;

    protected Head head;
    
    @Attribute(required = false)
    protected double doubleValueMinimum = 0;
    
    @Attribute(required = false)
    protected double doubleValueMaximum = 1;
    
    @Attribute(required = false)
    protected double doubleValueStep = 0.1;

    public AbstractActuator() {
        this.id = Configuration.createId("ACT");
        this.name = getClass().getSimpleName();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Head getHead() {
        return head;
    }

    @Override
    public void setHead(Head head) {
        this.head = head;
    }

    @Override
    public Location getCameraToolCalibratedOffset(Camera camera) {
        return new Location(camera.getUnitsPerPixel().getUnits());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        firePropertyChange("name", null, name);
    }

    @Override
    public Icon getPropertySheetHolderIcon() {
        return null;
    }

    @Override
    public void moveTo(Location location) throws Exception {
        moveTo(location, getHead().getMachine().getSpeed());
    }

    @Override
    public void moveToSafeZ() throws Exception {
        moveToSafeZ(getHead().getMachine().getSpeed());
    }

    @Override
    public String read() throws Exception {
        return null;
    }

    @Override
    public double getDoubleValueMinimum() {
        return doubleValueMinimum;
    }

    public void setDoubleValueMinimum(double doubleValueMinimum) {
        this.doubleValueMinimum = doubleValueMinimum;
    }

    @Override
    public double getDoubleValueMaximum() {
        return doubleValueMaximum;
    }

    public void setDoubleValueMaximum(double doubleValueMaximum) {
        this.doubleValueMaximum = doubleValueMaximum;
    }

    @Override
    public double getDoubleValueStep() {
        return doubleValueStep;
    }

    public void setDoubleValueStep(double doubleValueStep) {
        this.doubleValueStep = doubleValueStep;
    }
}
