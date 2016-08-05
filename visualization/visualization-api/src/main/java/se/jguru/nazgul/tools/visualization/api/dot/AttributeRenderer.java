package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.AbstractAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.TokenValueHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>Attribute renderer which sorts its key/value pairs alphabetically, corresponding to
 * the DOT grammar specification below. This implementation only uses comma for
 * delimiters ({@link #DELIMITER}.</p>
 * <pre>
 *     attr_list : '[' [ a_list ] ']' [ attr_list ]
 *     a_list    : ID '=' ID [ (';' | ',') ] [ a_list ]
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     [ sides="7", distortion="-0.687574", orientation="58", skew="-0.180116", color="lightsteelblue1" ] ;
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class AttributeRenderer extends AbstractStringRenderer<AbstractAttributes> {

    /**
     * The token starting an AttributeList.
     */
    public static final String START_TOKEN = "[ ";

    /**
     * The token ending an AttributeList.
     */
    public static final String END_TOKEN = " ]";

    /**
     * The token separating attribute (key/value) pairs from one another.
     */
    public static final String DELIMITER = ", ";

    /**
     * The token separating key and value attributes from one another.
     */
    public static final String SEPARATOR = "=";

    /**
     * Default constructor.
     */
    public AttributeRenderer() {
        super(AbstractAttributes.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final AbstractAttributes entity) {

        // Collect all non-null properties into a SortedMap using reflection.
        final SortedMap<String, Object> properties = new TreeMap<>();
        for (Class<?> current = entity.getClass();
             current != null && current != Object.class;
             current = current.getSuperclass()) {

            for (Field currentField : current.getDeclaredFields()) {
                if (isConfigurationField(currentField)) {

                    final String key = currentField.getName();

                    try {

                        final Object value = currentField.get(entity);
                        properties.put(key, value);

                    } catch (Exception e) {
                        throw new IllegalArgumentException("Could not add configuration for field [" + key
                                + "] in class [" + entity.getClass().getSimpleName() + "]", e);
                    }
                }
            }
        }

        // Now, transform all model properties into DOT configuration properties.
        final SortedMap<String, String> attributes = transformIntoDotConfigurationMap(properties);

        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> current : attributes.entrySet()) {
            builder.append(current.getKey() + SEPARATOR + "\"" + current.getValue() + "\"" + DELIMITER);
        }

        // Remove the last delimiter
        builder.delete(builder.length() - DELIMITER.length(), builder.length());

        // All Done.
        builder.append(END_TOKEN);
        return builder.toString();
    }

    //
    // Private helpers
    //

    private boolean isConfigurationField(final Field aField) {

        final int modifiers = aField.getModifiers();
        return Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers);
    }

    private SortedMap<String, String> transformIntoDotConfigurationMap(final SortedMap<String, Object> propMap) {

        final SortedMap<String, String> toReturn = new TreeMap<>();
        for (Map.Entry<String, Object> current : propMap.entrySet()) {
            toReturn.put(getDotConfigKeyFor(current.getKey()), getDotConfigValueFor(current.getValue()));
        }

        // All Done.
        return toReturn;
    }

    private String getDotConfigKeyFor(final String modelKey) {

    }

    private String getDotConfigValueFor(final Object modelValue) {

        // #1) If the modelValue corresponds to a specific type, render that type
        if(modelValue instanceof StandardCssColor) {
            return ((StandardCssColor) modelValue).getRgbValue();
        }
        if(modelValue instanceof TokenValueHolder) {
            return ((TokenValueHolder) modelValue).getTokenValue();
        }

        // #2) If the modelValue is a String or primitive, simply use it.
        if(modelValue instanceof String || modelValue.getClass().isPrimitive()) {
            return "" + modelValue;
        }
        if(modelValue.getClass().getName().startsWith("java.lang.")) {
            return "" + modelValue;
        }

        // Unknown
        throw new IllegalArgumentException("Could not convert modelValue [" + modelValue
                + "] to a DOT configuration value.");
    }
}
