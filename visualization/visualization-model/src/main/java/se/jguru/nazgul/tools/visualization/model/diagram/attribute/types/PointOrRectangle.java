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
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>A 2D Point or Rectangle with a {@link #toString()} method conforming to the
 * <a href="http://www.graphviz.org/content/attrs#kpoint">Dot Point specification</a>:</p>
 * <p>
 * <strong><code>"%f,%f('!')?"</code></strong> representing the point (xOrWidth,yOrHeight).
 * The optional '!' indicates the node position should not change (input-only).
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"xOrWidth", "yOrHeight", "isUnchangeable"})
@XmlAccessorType(XmlAccessType.FIELD)
public class PointOrRectangle implements Serializable {

    /**
     * The X-value (or width) of this PointOrRectangle.
     */
    @XmlAttribute(required = true)
    private double xOrWidth;

    /**
     * The Y-value (or height) of this PointOrRectangle.
     */
    @XmlAttribute(required = true)
    private double yOrHeight;

    /**
     * If true, then indicates that this {@link PointOrRectangle} is unchangeable.
     */
    @XmlAttribute(required = true)
    private boolean isUnchangeable;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public PointOrRectangle() {
        // Do nothing
    }

    /**
     * Compound constructor creating a changeable PointOrRectangle as defined by the Dot specification.
     *
     * @param xOrWidth  The X-value (or width) of this PointOrRectangle.
     * @param yOrHeight The Y-value (or height) of this PointOrRectangle.
     */
    public PointOrRectangle(final double xOrWidth, final double yOrHeight) {
        this(xOrWidth, yOrHeight, false);
    }

    /**
     * Compound constructor creating a Point (or Rectangle) as defined by the Dot specification.
     *
     * @param xOrWidth       The X-value (or width) of this PointOrRectangle.
     * @param yOrHeight      The Y-value (or height) of this PointOrRectangle.
     * @param isUnchangeable {@code true} to make this PointOrRectangle unchangeable.
     */
    public PointOrRectangle(final double xOrWidth, final double yOrHeight, final boolean isUnchangeable) {
        this.xOrWidth = xOrWidth;
        this.yOrHeight = yOrHeight;
        this.isUnchangeable = isUnchangeable;
    }

    /**
     * Assigns this Point to be unchangeable, i.e. read-only.
     *
     * @param isUnchangeable {@code true} to make this Point unchangeable.
     */
    public void setUnchangeable(final boolean isUnchangeable) {
        this.isUnchangeable = isUnchangeable;
    }

    /**
     * @return The X-value (or width) of this PointOrRectangle.
     */
    public double getxOrWidth() {
        return xOrWidth;
    }

    /**
     *
     * @return The Y-value (or height) of this PointOrRectangle.
     */
    public double getyOrHeight() {
        return yOrHeight;
    }

    /**
     * @return true if this PointOrRectangle is defined as unchangeable, i.e. readonly.
     */
    public boolean isUnchangeable() {
        return isUnchangeable;
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
        final PointOrRectangle that = (PointOrRectangle) o;
        return Double.compare(that.xOrWidth, xOrWidth) == 0
                && Double.compare(that.yOrHeight, yOrHeight) == 0
                && isUnchangeable == that.isUnchangeable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(xOrWidth, yOrHeight, isUnchangeable);
    }

    /**
     * {@code}
     */
    @Override
    public String toString() {
        final String xValue = BigDecimal.valueOf(xOrWidth).toPlainString();
        final String yValue = BigDecimal.valueOf(yOrHeight).toPlainString();
        return "[PointOrRectangle]: (" + xValue + "," + yValue + ")" + (isUnchangeable() ? "!" : "");
    }
}
