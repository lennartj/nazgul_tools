package se.jguru.nazgul.tools.visualization.api.diagram.statement.attribute;

import se.jguru.nazgul.tools.visualization.api.diagram.AbstractStringIdentifiable;
import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.attribute.NodeAttributeList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Attribute statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     attr_stmt : node attr_list
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     node [	fontname = "Helvetica-Oblique",
 *              fontsize = "36",
 *              label = "\n\n\n\nObject Oriented Graphs\nStephen North, 3/19/93",
 *              size = "6,6" ];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"attributes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeAttribute extends AbstractStringIdentifiable implements AttributeStatement {

    // Internal state
    @XmlElement
    private NodeAttributeList attributes;

    /**
     * Default constructor
     */
    public NodeAttribute() {

        // Delegate
        super("node");

        // Assign internal state
        this.attributes = new NodeAttributeList();
    }

    /**
     * Retrieves the {@link NodeAttributeList} which can be used to add attributes to this {@link NodeAttribute}
     * Statement.
     *
     * @return the {@link NodeAttributeList} which can be used to add attributes to this {@link NodeAttribute}
     * Statement.
     */
    public NodeAttributeList getAttributes() {
        return attributes;
    }

    /**
     * <p>Renders this AttributeStatement on the following form:</p>
     * <pre>node attr_list</pre>
     */
    @Override
    public String render() {
        return getId() + " " + attributes.render();
    }
}
