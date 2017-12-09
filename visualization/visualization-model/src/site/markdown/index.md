# Nazgul Tools: Visualization Model

The Visualization Model defines entities containing properties from the [Graphviz/Dot](http://www.graphviz.org)
diagram specification. All entity classes are JAXB-annotated to ensure that the model can be converted in a simple 
manner to and from XML and JSON formats. This has the added benefit of providing an XML Schema which defines the XML
configuration properly - the XSD (called `nazgul-visualization.xsd`) is generated from the JAXB-annotated entity 
classes by the [JAXB2 Maven Plugin](http://www.mojohaus.org/jaxb2-maven-plugin/Documentation/v2.2/).

## Overview: Graphviz Terminology Definitions
 
Please refer to [Graphviz/Dot](http://www.graphviz.org) for complete definitions. This section contains a brief 
overview, related to the Nazgul Visualization Model.
  
### 1. Statements

In Graphviz terminology, "Graph"s and "Subgraph"s can contains "Statements". Statements define elements within the 
Graphs, and are related by type implementation in the Nazgul Visualization Model; all Statement types 
implement the `Statement` interface. As illustrated in the image below, there are 7 types of Statement:

<img src="images/statement_structure.png" style="border: solid DarkGray 1px;" />

1. Nodes are the basic concepts within graphs
2. Edges are links/relations between Nodes and/or Subgraphs
3. Subgraphs group Nodes, Edges or other Subgraphs. 
4. Identifiers are used to give aliases to Nodes
5. CommonNodeAttributes, CommonGraphAttributes and CommonEdgeAttributes hold attributes which are applied to all 
   Nodes, Graphs and Edges respectively.
   
### 2. Graphs and Subgraphs   

The two Statement containers - Graph and Subgraph - contains all Statement objects within a `Statements` holder,
which exposes methods to simplify finding and creating some statements. Graphs and Subgraphs must have a unique 
identifier, which is provided in a homogenous manner by the AbstractIdentifiable class as shown in the image below:
 
<img src="images/graph_structure.png" style="border: solid DarkGray 1px;" />

### 3. Attributes

Each Node, Edge and (Sub)Graph can hold attributes which inform the renderer about particular settings for the object
in question. Nodes are defined by the optional presence of a NodeAttributes, Edges by EdgeAttributes and (Sub)Graphs 
by GraphAttributes respectively. These attribute classes are related by inheritance, and as illustrated in the 
image above, each type of Statement may contain its appropriate attributes holder class.  

<img src="images/attribute_structure.png" style="border: solid DarkGray 1px;" />

## Example: Creating a Graph

The simplest example of creating a Graph containing two nodes 

    // Create the Graph parent.
    final boolean directed = true;
    final boolean strict = true;
    final Graph graph = new Graph("Example", directed, strict);
    
    // Add two Nodes to the Graph
    final Node node1 = new Node("node_1");
    final Node node2 = new Node("node_2");
    graph.add(node1, node2);
            
    // Add an extra identifier for node 1.
    graph.add(new Identifier(node1.getId(), "foobar"));
            
    // Add an Edge between Node1 and Node2.
    // Note: use the IDs of the from and to Nodes as arguments.  
    graph.addEdge("node_1", "node_2");

When given to a Graphviz source renderer (typically the Nazgul Visualization API), the Graph above should be 
converted to the following Dot source file:
     
        strict digraph "Example" { 
        
            // Node statements.
            "node_1" ;
            "node_2" ;
        
            // Identifier statements.
            "node_1" = "foobar" ;
        
            // Edge statements.
            "node_1" -> "node_2" ;
        }      
        
Note the structural correspondence/similarity between the Java source code of the Nazgul Tools Visualization Model 
above and its corresponding Graphviz source file. When run through Graphviz, the source file above produces an image 
showing the default representation of the two Nodes with the Edge binding them together:

<img src="images/exampleGraph.png" style="border: solid DarkGray 1px;" />    