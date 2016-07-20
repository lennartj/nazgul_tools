package se.jguru.nazgul.tools.visualization.api.diagram.attribute.model;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p>An enumeration containing the possible values for the arrow type definition as
 * specified by the <a href="http://www.graphviz.org/content/attrs#karrowType">Dot arrowtype definition</a>.
 * From the Dot documentation:</p>
 *
 * <p>These are the basic set of backward-compatible arrow shapes. In addition, there is a grammar of arrow shapes
 * which can be used to describe a collection of 2,313,441 arrow combinations of the 39 varations of the primitive
 * set of 10 arrows. The basic arrows shown above contain all of the primitive shapes (box, crow, circle, diamond,
 * dot, inv, none, normal, tee, vee) plus ones that can be derived from the grammar (odot, invdot, invodot, obox,
 * odiamond) plus some supported as special cases for backward-compatibility (ediamond, open, halfopen, empty,
 * invempty).</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(name = Graph.NAMESPACE)
@XmlEnum(String.class)
public enum ArrowType implements Serializable {

    NORMAL("normal"),

    INVERTED("inv"),

    DOT("dot"),

    INVERTED_DOT("invdot"),

    UNFILLED_DOT("odot"),

    INVERTED_UNFILLED_DOT("invodot"),

    NONE("none"),

    TEE("tee"),

    EMPTY("empty"),

    INVERTED_EMPTY("invempty"),

    DIAMOND("diamond"),

    UNFILLED_DIAMOND("odiamond"),

    E_DIAMOND("ediamond"),

    CROW("crow"),

    BOX("box"),

    UNFILLED_BOX("obox"),

    OPEN("open"),

    HALF_OPEN("halfopen"),

    VEE("vee"),

    CIRCLE("circle");

    // Internal state
    private String dotConfigValue;

    ArrowType(final String dotConfigValue) {
        this.dotConfigValue = dotConfigValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return dotConfigValue;
    }
}
