/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *       http://www.jguru.se/licenses/jguruCorporateSourceLicense-2.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.AbstractAttributes;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.DotProperty;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.PointOrRectangle;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.StandardCssColor;
import se.jguru.nazgul.tools.visualization.model.diagram.attribute.types.TokenValueHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
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
        final SortedMap<String, String> dotAttributes = new TreeMap<>();

        for (Class<?> current = entity.getClass();
             current != null && current != Object.class;
             current = current.getSuperclass()) {

            for (Field currentField : current.getDeclaredFields()) {
                if (isConfigurationField(currentField)) {

                    String key = null;
                    Object value = null;

                    try {

                        // #1) Do we have a non-null value in the Field?
                        value = currentField.get(entity);
                        if (value == null) {
                            continue;
                        }

                        // #2) Do we have @DotProperty annotation on the Field?
                        final DotProperty dotPropertyAnnotation = currentField.getAnnotation(DotProperty.class);
                        if (dotPropertyAnnotation == null) {
                            continue;
                        }

                        // #3) Use the DotProperty "name" attribute, or fallback to the field name.
                        key = dotPropertyAnnotation.name();
                        if (key.isEmpty() || "##default".equalsIgnoreCase(key)) {
                            key = currentField.getName();
                        }

                        // #4) If this is a special case (multiple Dot properties combined to
                        //     1 model property), handle it.
                        if (key == null) {

                            if ("labelSize".equals(currentField.getName())
                                    && PointOrRectangle.class.equals(currentField.getType())) {

                                final PointOrRectangle rect = (PointOrRectangle) value;
                                dotAttributes.put("lwidth", BigDecimal.valueOf(rect.getxOrWidth()).toPlainString());
                                dotAttributes.put("lheight", BigDecimal.valueOf(rect.getyOrHeight()).toPlainString());

                                // All Done for this Field.
                                continue;
                            }
                        }

                        // #5) Transform the value if required, and add the key/value pair to the outbound Map.
                        dotAttributes.put(key, getDotConfigValueFor(value));

                    } catch (Exception e) {
                        throw new IllegalArgumentException("Could not add configuration for field ["
                                + currentField + "] --> [" + key + "] in class ["
                                + entity.getClass().getSimpleName() + "]", e);
                    }
                }
            }
        }

        // Check sanity
        if(dotAttributes.isEmpty()) {
            return "";
        }

        // Synthesize the required attribute List form.
        final StringBuilder builder = new StringBuilder(START_TOKEN);
        for (Map.Entry<String, String> current : dotAttributes.entrySet()) {
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

    private String getDotConfigValueFor(final Object modelValue) {

        // #1) If the modelValue corresponds to a specific type, render that type
        if (modelValue instanceof StandardCssColor) {
            return ((StandardCssColor) modelValue).getRgbValue();
        }
        if (modelValue instanceof TokenValueHolder) {
            return ((TokenValueHolder) modelValue).getTokenValue();
        }

        // #2) If the modelValue is a String or primitive, simply use it.
        if (modelValue instanceof String || modelValue.getClass().isPrimitive()) {
            return "" + modelValue;
        }
        if (modelValue.getClass().getName().startsWith("java.lang.")) {
            return "" + modelValue;
        }

        // Unknown
        throw new IllegalArgumentException("Could not convert modelValue [" + modelValue
                + "] to a DOT configuration value.");
    }
}
