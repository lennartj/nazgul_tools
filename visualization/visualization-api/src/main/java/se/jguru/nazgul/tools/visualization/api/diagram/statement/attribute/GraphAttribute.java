package se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute;

import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.GraphAttributeList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Attribute statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     attr_stmt : graph attr_list
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     graph [	fontname = "Helvetica-Oblique",
 *              fontsize = "36",
 *              label = "\n\n\n\nObject Oriented Graphs\nStephen North, 3/19/93",
 *              size = "6,6" ];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"attributes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class GraphAttribute extends AbstractStringIdentifiable implements AttributeStatement {

    // Internal state
    @XmlElement
    private GraphAttributeList attributes;

    /**
     * Default constructor
     */
    public GraphAttribute() {

        // Delegate
        super("graph");

        // Assign internal state
        this.attributes = new GraphAttributeList();
    }

    /**
     * Retrieves the {@link GraphAttributeList} which can be used to add attributes to this Graph Attribute Statement.
     *
     * @return the {@link GraphAttributeList} which can be used to add attributes to this {@link GraphAttribute}
     * Statement.
     */
    public GraphAttributeList getAttributes() {
        return attributes;
    }

    /**
     * <p>Renders this AttributeStatement on the following form:</p>
     * <pre>graph attr_list</pre>
     */
    @Override
    public String render() {
        return getId() + " " + attributes.render();
    }
}
