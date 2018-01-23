/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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

package se.jguru.nazgul.tools.visualization.model.diagram;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Port which corresponds to the following
 * <strong><code>port : ':' ID [ ':' compass_pt ] | ':' compass_pt</code></strong> in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"id", "compassPoint"})
@XmlEnum(String.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Port implements Serializable {

    /**
     * An enumeration corresponding to
     * <strong><code>compass_pt : (n | ne | e | se | s | sw | w | nw | c | _)</code></strong> in the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     */
    @XmlType(namespace = AbstractIdentifiable.NAMESPACE)
    @XmlEnum(String.class)
    public enum CompassPoint implements Serializable {

        /**
         * Anchor to the north (i.e. top/center).
         */
        NORTH("n"),

        /**
         * Anchor to the northeast (i.e. top/right).
         */
        NORTH_EAST("ne"),

        /**
         * Anchor to the east (i.e. center/right).
         */
        EAST("e"),

        /**
         * Anchor to the southeast (i.e. bottom/right).
         */
        SOUTH_EAST("se"),

        /**
         * Anchor to the south (i.e. bottom/center).
         */
        SOUTH("s"),

        /**
         * Anchor to the south west (i.e. bottom/left).
         */
        SOUTH_WEST("sw"),

        /**
         * Anchor to the west (i.e. center/left).
         */
        WEST("w"),

        /**
         * Anchor to the north west (i.e. top/left).
         */
        NORTH_WEST("nw"),

        /**
         * Anchor to the south west (i.e. center/center).
         */
        CENTER("c"),

        /**
         * Unspecified positioning.
         */
        UNKNOWN("_");

        // Internal state
        private String dotToken;

        CompassPoint(final String dotToken) {
            this.dotToken = dotToken;
        }

        /**
         * Retrieves the DOT specification token of this CompassPoint.
         *
         * @return the DOT specification token of this CompassPoint.
         */
        public String getDotToken() {
            return dotToken;
        }
    }

    /**
     * An optional Identifier of this Port.
     */
    @XmlElement
    private String id;

    /**
     * The optional {@link CompassPoint} of this Port.
     */
    @XmlElement
    private CompassPoint compassPoint;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public Port() {
        // Do nothing
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
        this.id = identifier;
        this.compassPoint = compassPoint;
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
     * Retrieves the optional (i.e. nullable) identifier of this Port.
     *
     * @return the optional (i.e. nullable) identifier of this Port.
     */
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {

        // Fail fast
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // Delegate to internal state
        final Port port = (Port) o;
        return Objects.equals(getId(), port.getId())
                && getCompassPoint() == port.getCompassPoint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, compassPoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final String idString = getId() == null ? "<anonymous>" : getId();
        return "[" + getClass().getSimpleName() + "]: " + idString + ", CompassPoint: " + getCompassPoint();
    }
}
