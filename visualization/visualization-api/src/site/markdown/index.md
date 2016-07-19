# Nazgul Tools: Visualization API

The Visualization API contains builders implemented to comply with the Graphviz/Dot
diagram specification. The builders create commented and structured versions of all
diagram nodes, subgraphs, attributes and edges. 

Typical usages are illustrated throughout the unit tests; two examples are shown below:

## 1. Create a simple Graph 

Creating a diagram file with a directed graph containing 2 nodes and a (directed) Edge
between them is fairly trivial:

<pre class="brush: java" title="Example Graph"><![CDATA[

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
        
        // Render the Graph to create a Graphviz/Dot diagram
        final String diagram = graph.render();
]]></pre>

The example above produces the following diagram (comments are rendered "for free"):
        
        strict digraph "Example" { 
        
            // Node statements.
            "node_1"  ;
            "node_2"  ;
        
            // Identifier statements.
            "node_1"  = "foobar"  ;
        
            // Edge statements.
            "node_1"  ->  "node_2"  ;
        } 

... which, in turn, produces the following graph when run through GraphViz:

<img src="images/exampleGraph.png" style="margin:10px;" />

## 2. Assigning Attributes

Most Statements within the Dot language can hold Attributes, providing greater
control over the final rendering result. The example below adds a few Attributes,
both to the Graph, the Nodes and to one of the Edges:

<pre class="brush: java" title="Example Graph with Attributes"><![CDATA[

        // Create a directed and strict Graph parent.
        // Add some default attributes for the Graph and for its Nodes.
        //
        final Graph graph = new Graph("Example", true, true);
        graph.add(Attribute.AttributeType.GRAPH.get().addAttribute("bgcolor", "#eeeeee"));
        graph.add(Attribute.AttributeType.NODE.get()
                .addAttribute("color", "#555599")
                .addAttribute("fillcolor", "#cccccc")
                .addAttribute("penwidth", "2.0")
                .addAttribute("style", "filled"));

        // Add some Nodes to the Graph
        // Add some Attributes to override the standard ones.
        //
        final List<Node> nodes = Stream.of("First", "Second", "Third")
                .map(c -> new Node(c + "_Node"))
                .collect(Collectors.toList());

        nodes.get(0).getAttributes()
                .addAttribute("color", "#aa0000")
                .addAttribute("fillcolor", "#ffffff");

        nodes.get(2).getAttributes()
                .addAttribute("color", "#ffffff")
                .addAttribute("fillcolor", "#333333")
                .addAttribute("fontcolor", "#ffffff");

        graph.add(nodes.toArray(new Node[nodes.size()]));

        // Add Edges between the Nodes
        // Override some Attributes on the second Edge.
        //
        graph.addEdge("First_Node", "Second_Node");
        final Edge secondEdge = graph.addEdge("Second_Node", "Third_Node");
        graph.addEdge("Third_Node", "First_Node");

        secondEdge.getAttributes()
                .addAttribute("color", "#0000ff")
                .addAttribute("penwidth", "2.0");

        // Render the Graph to create a Graphviz/Dot diagram
        //
        final String diagram = graph.render();
        System.out.println("Got:\n" + diagram);
]]></pre>

The example above produces the following diagram:

    strict digraph "Example" {

        // Attribute statements.
        graph [ bgcolor="#eeeeee" ] ;
        node [ color="#555599", fillcolor="#cccccc", penwidth="2.0", style="filled" ] ;

        // Node statements.
        "First_Node"  [ color="#aa0000", fillcolor="#ffffff" ] ;
        "Second_Node" ;
        "Third_Node"  [ color="#ffffff", fillcolor="#333333", fontcolor="#ffffff" ] ;

        // Edge statements.
        "First_Node"  ->  "Second_Node"  ;
        "Second_Node"  ->  "Third_Node"  [ color="#0000ff", penwidth="2.0" ] ;
        "Third_Node"  ->  "First_Node"  ;
    }
    
... which, in turn, produces the following graph when run through GraphViz:

<img src="images/exampleAttributesGraph.png" style="margin:10px;" />    
