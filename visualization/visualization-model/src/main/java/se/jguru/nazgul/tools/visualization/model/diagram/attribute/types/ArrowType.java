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


package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Entity specifying the shape of an Arrow. According to the Dot documentation:</p>
 * <p>Arrow shapes can be specified and named using the following simple grammar. Terminals are shown in bold font and
 * nonterminals in italics. Literal characters are given in single quotes. Square brackets [ and ] enclose optional
 * items. Vertical bars | separate alternatives.</p>
 * <pre>
 *     <i>arrowname : aname [ aname [ aname [ aname ] ] ]
 *     aname     : [ modifiers ] shape
 *     modifiers : [</i> '<b>o</b>' ] [ <i>side</i> ]
 *     <i>side</i>      : '<b>l</b>' | '<b>r</b>'
 *     <i>shape</i>     : <b>box</b>
 *               | <b>crow</b>
 *               | <b>curve</b>
 *               | <b>icurve</b>
 *               | <b>diamond</b>
 *               | <b>dot</b>
 *               | <b>inv</b>
 *               | <b>none</b>
 *               | <b>normal</b>
 *               | <b>tee</b>
 *               | <b>vee</b>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"isFilled", "clipSide", "shape"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrowType implements Serializable {

    /**
     * Enumeration defining the possible Clip settings defined for
     * <a href="http://www.graphviz.org/content/arrow-shapes">Arrow shapes</a>.
     */
    @XmlType(namespace = AbstractIdentifiable.NAMESPACE)
    @XmlEnum(String.class)
    public enum Clip implements TokenValueHolder {

        /**
         * Clip the shape, leaving only the part to the left of the edge.
         */
        LEFT("l"),

        /**
         * Clip the shape, leaving only the part to the right of the edge.
         */
        RIGHT("r");

        // Internal state
        private String dotToken;

        Clip(final String dotToken) {
            this.dotToken = dotToken;
        }

        /**
         * Retrieves the DOT configuration value for the supplied Clip.
         *
         * @return the non-empty DOT configuration value for the supplied Clip.
         */
        @Override
        public String getTokenValue() {
            return dotToken;
        }
    }

    /**
     * Enumeration defining the possible Shapes defined for
     * <a href="http://www.graphviz.org/content/arrow-shapes">Arrows</a>.
     */
    @XmlType(namespace = AbstractIdentifiable.NAMESPACE)
    @XmlEnum(String.class)
    public enum Shape implements TokenValueHolder {

        BOX,

        CROW,

        CURVE,

        ICURVE,

        DIAMOND,

        DOT,

        INV,

        NONE,

        NORMAL,

        TEE,

        VEE;

        /**
         * Retrieves the DOT configuration value for the supplied Shape.
         *
         * @return the non-empty DOT configuration value for the supplied Clip.
         */
        @Override
        public String getTokenValue() {
            return name().toLowerCase();
        }
    }

    /**
     * Indicates if a filled (not "open") version of the ArrowType should be used.
     */
    @XmlAttribute
    private boolean isFilled;

    /**
     * If non-null, indicates that this ArrowType should be clipped at one of its sides.
     */
    @XmlAttribute
    private Clip clipSide;

    /**
     * The non-null shape of this {@link ArrowType}
     */
    @XmlAttribute(required = true)
    private Shape shape;

    /**
     * Default constructor, creating a normal, filled and non-clipped {@link ArrowType}.
     */
    public ArrowType() {
        this(true, Shape.NORMAL);
    }

    /**
     * Convenience constructor creating a non-clipped ArrowType with the supplied {@link Shape} and fill.
     *
     * @param isFilled Indicates if a filled (not "open"/"non-filled") version of the ArrowType should be used.
     * @param shape    The non-null Shape of this ArrowType.
     */
    public ArrowType(final boolean isFilled, final Shape shape) {
        this(isFilled, null, shape);
    }

    /**
     * Compound constructor creating an ArrowType with the supplied shape and
     *
     * @param isFilled Indicates if a filled (not "open"/"non-filled") version of the ArrowType should be used.
     * @param clipSide An optional Clip definition, used to indicate if the arrow type should be clipped to the
     *                 right or left of its centerpoint.
     * @param shape    The non-null Shape of this ArrowType.
     */
    public ArrowType(final boolean isFilled, final Clip clipSide, final Shape shape) {

        // Check sanity
        if (shape == null) {
            throw new IllegalArgumentException("Cannot handle null 'shape' argument.");
        }

        // Assign internal state
        this.isFilled = isFilled;
        this.clipSide = clipSide;
        this.shape = shape;
    }

    /**
     * @return true to indicate a filled (not "open") version of the ArrowType should be used.
     */
    public boolean isFilled() {
        return isFilled;
    }

    /**
     * Retrieves an optional (i.e. nullable) Clip definition, used to indicate if the arrow type should be clipped to
     * the right or left of its center.
     *
     * @return an optional (i.e. nullable) Clip definition, used to indicate if the arrow type should be clipped to
     * the right or left of its center.
     */
    public Clip getClipSide() {
        return clipSide;
    }

    /**
     * Retrieves the {@link Shape} of this ArrowType.
     *
     * @return The non-null {@link Shape} of this ArrowType.
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final String clipInfo = (getClipSide() == null ? "" : ", clipSide: " + getClipSide());
        return "ArrowType [shape: " + getShape().getTokenValue() + ", filled: " + isFilled + clipInfo + "]";
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
        final ArrowType arrowType = (ArrowType) o;
        return isFilled() == arrowType.isFilled()
                && getClipSide() == arrowType.getClipSide()
                && getShape() == arrowType.getShape();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(isFilled, clipSide, shape);
    }
}
