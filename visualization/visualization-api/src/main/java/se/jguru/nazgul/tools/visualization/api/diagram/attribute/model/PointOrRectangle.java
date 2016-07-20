package se.jguru.nazgul.tools.visualization.api.diagram.attribute.model;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>A 2D Point or Rectangle with a {@link #toString()} method conforming to the
 * <a href="http://www.graphviz.org/content/attrs#kpoint">Dot Point specification</a>:</p>
 *
 * <strong><code>"%f,%f('!')?"</code></strong> representing the point (x,y).
 * The optional '!' indicates the node position should not change (input-only).
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(name = Graph.NAMESPACE)
@XmlEnum(String.class)
public class PointOrRectangle implements Serializable {

    // Internal state
    private double x;
    private double y;
    private boolean isUnchangeable;

    /**
     * JAXB-friendly constructor, <strong>reserved for framework use.</strong>
     */
    public PointOrRectangle() {
        // Do nothing
    }

    /**
     * Compound constructor creating a Point as defined by the Dot specification.
     *
     * @param x The X-value of this Point.
     * @param y The Y-value of this Point.
     */
    public PointOrRectangle(final double x, final double y) {
        this.x = x;
        this.y = y;
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
     * {@code}
     */
    @Override
    public String toString() {
        final String xValue = BigDecimal.valueOf(x).toPlainString();
        final String yValue = BigDecimal.valueOf(y).toPlainString();
        return "" + xValue + "," + yValue + (isUnchangeable ? "!" : "");
    }
}
