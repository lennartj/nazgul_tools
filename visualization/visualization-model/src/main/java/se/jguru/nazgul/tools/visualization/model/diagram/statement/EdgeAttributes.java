package se.jguru.nazgul.tools.visualization.model.diagram.statement;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>Attribute statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     attr_stmt : edge attr_list
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     edge [	fontname = "Helvetica-Oblique",
 *              fontsize = "36",
 *              label = "\n\n\n\nObject Oriented Graphs\nStephen North, 3/19/93",
 *              size = "6,6" ];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 * @see EdgeAttributeList
 */
public class EdgeAttributes extends AbstractIdentifiable implements Statement {

    /**
     * The list of key/value pair attributes used for Edges.
     */
    @XmlElement
    private EdgeAttributeList attributes;

    /**
     * Default constructor.
     */
    public EdgeAttributes() {

        // Delegate
        super("edge");

        // Assign internal state
        this.attributes = new EdgeAttributeList();
    }

    /**
     * Retrieves the {@link EdgeAttributeList} which can be used to add attributes to this
     * {@link EdgeAttribute} Statement.
     *
     * @return the {@link EdgeAttributeList} which can be used to add attributes to this
     * {@link EdgeAttribute} Statement.
     */
    public EdgeAttributeList getAttributes() {
        return attributes;
    }
}
