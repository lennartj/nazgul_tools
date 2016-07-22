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
package se.jguru.nazgul.tools.visualization.api.diagram;

import se.jguru.nazgul.tools.visualization.api.StringRenderable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * A Port which corresponds to the following
 * <strong><code>port : ':' ID [ ':' compass_pt ] | ':' compass_pt</code></strong> in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(name = Graph.NAMESPACE, propOrder = {"id", "compassPoint"})
@XmlEnum(String.class)
public class Port implements StringRenderable {

    /**
     * An enumeration corresponding to
     * <strong><code>compass_pt : (n | ne | e | se | s | sw | w | nw | c | _)</code></strong> in the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     */
    @XmlType(name = Graph.NAMESPACE)
    @XmlEnum(String.class)
    public enum CompassPoint implements StringRenderable {
        NORTH("n"),
        NORTH_EAST("ne"),
        EAST("e"),
        SOUTH_EAST("se"),
        SOUTH("s"),
        SOUTH_WEST("sw"),
        WEST("w"),
        NORTH_WEST("nw"),
        CENTER("c"),
        UNKNOWN("_");

        // Internal state
        private String dotToken;

        CompassPoint(final String dotToken) {
            this.dotToken = dotToken;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String render() {
            return dotToken;
        }
    }

    // Internal state
    @XmlElement(required = true)
    private String identifier;

    @XmlElement
    private CompassPoint compassPoint;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Port() {
        // Do nothing.
    }

    /**
     * Convenience constructor for an anonymous Port with the supplied {@link CompassPoint}.
     *
     * @param compassPoint A non-null {@link CompassPoint} value.
     */
    public Port(final CompassPoint compassPoint) {
        this(null, compassPoint);
    }

    /**
     * Compound constructor, creating a Port wrapping the supplied data.
     *
     * @param identifier   An optional (i.e. nullable) identifier for this Port.
     * @param compassPoint A non-null {@link CompassPoint} value.
     */
    public Port(final String identifier, final CompassPoint compassPoint) {

        // Check sanity
        if (compassPoint == null) {
            throw new IllegalArgumentException("Cannot handle null 'compassPoint' argument.");
        }

        // Assign internal state
        this.identifier = identifier;
        this.compassPoint = compassPoint;
    }

    /**
     * Retrieves the optional (i.e. nullable) identifier of this NodeID.
     *
     * @return the optional (i.e. nullable) identifier of this NodeID.
     */
    public String getId() {
        return identifier;
    }

    /**
     * The non-null CompassPoint of this Port.
     *
     * @return the non-null CompassPoint of this Port.
     */
    public CompassPoint getCompassPoint() {
        return compassPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render() {

        final String prefix = identifier != null && !identifier.isEmpty()
                ? ": " + identifier + " "
                : "";

        // All Done.
        return prefix + ": " + compassPoint.render();
    }
}
