package org.openpnp.util;

import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class BeanUtils {
    public static boolean addPropertyChangeListener(Object obj, String property, PropertyChangeListener listener) {
        try {
            Method method = obj.getClass().getMethod("addPropertyChangeListener", String.class, PropertyChangeListener.class);
            method.invoke(obj, property, listener);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static boolean addPropertyChangeListener(Object obj, PropertyChangeListener listener) {
        try {
            Method method = obj.getClass().getMethod("addPropertyChangeListener", PropertyChangeListener.class);
            method.invoke(obj, listener);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static boolean removePropertyChangeListener(Object obj, PropertyChangeListener listener) {
        try {
            Method method = obj.getClass().getMethod("removePropertyChangeListener", PropertyChangeListener.class);
            method.invoke(obj, listener);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static AutoBinding bind(UpdateStrategy updateStrategy, Object source, String sourceProperty,
            Object target, String targetProperty) {
        return bind(updateStrategy, source, sourceProperty, target, targetProperty, null);
    }

    public static AutoBinding bind(UpdateStrategy updateStrategy, Object source, String sourceProperty,
            Object target, String targetProperty, Converter converter) {
        AutoBinding binding = Bindings.createAutoBinding(updateStrategy, source,
                BeanProperty.create(sourceProperty), target, BeanProperty.create(targetProperty));
        if (converter != null) {
            binding.setConverter(converter);
        }
        binding.bind();
        return binding;
    }
    
    /**
     * Convert a Java Beans style property name such as "myGoodPropery" to a human readable display
     * name such as "My Good Property". Runs of capital letters are maintained so that properties
     * such as "sourceURI" display as "Source URI". Periods are treated as if concatenating multiple
     * properties together with a space. Note that periods cause this to be one way conversion. There
     * is no analog for converting back from a display name where the property contained a period.
     * @param propertyName
     * @return
     */
    public static String getPropertyDisplayName(String propertyName) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < propertyName.length(); i++) {
                char ch = propertyName.charAt(i);
                if (Character.isUpperCase(ch) && i > 0 && Character.isLowerCase(propertyName.charAt(i - 1))) {
                    sb.append(' ');
                }
                if (ch == '.') {
                    sb.append(' ');
                    continue;
                }
                if (i == 0 || propertyName.charAt(i - 1) == '.') {
                    ch = Character.toUpperCase(ch);
                }
                sb.append(ch);
            }
            return sb.toString();
        }
        catch (Exception e) {
            return propertyName;
        }
    }
    
    public static void test(String propertyName) {
        String dn = getPropertyDisplayName(propertyName);
        String dc = Introspector.decapitalize(dn.replaceAll(" ", ""));
        System.out.println(String.format("[%s] %20s %20s %20s",
                propertyName.equals(dc) ? "X" : " ",
                propertyName,
                dn, 
                dc));
    }
    
    public static void main(String[] args) {
        test("feedCount");
        test("sourceURI");
        test("sourceUri");
        test("part");
        test("partId");
        test("partID");
        test("uri");
        test("URI");
        test("exposure.value");
    }
}
