# Nazgul Tools: Visualization Dot SPI

The Visualization SPI contains a Graphviz/Dot grammar gratefully copied from the 
[ANTLR4 Grammars V4](https://github.com/antlr/grammars-v4) project, and modified to 
produce Java classes more in tune with the Java Language specification WRT case. This
project also contains some utility classes to validate the syntax of a stream containing
data conforming to the <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.

The typical two usage for this component is as follows:

## 1. Simple validation of Graphviz/Dot files

A typical implementation of a validateInternalState method follows:

<pre class="brush: java" title="Example validation"><![CDATA[
// Get an InputStream to the DOT file.
final InputStream dotStream = getClass().getClassLoader().getResourceAsStream("path/to/a/dotfile.dot");

// Validate that the dotStream conformed to the DOT grammar.  
try {
    DotDiagramValidator.validate(dotStream);
    
    // If we wind up here, the stream contained valid Dot data.
    
} catch(DotDiagramSyntaxException ex) {
    
    // If we wind up here, the stream did not conform to the DOT language specification.
}
]]></pre>

## 2. Handling parsed syntax tree data from Graphviz/Dot files

If you want to access the GraphContext produced by ANTLR4 when parsing the Dot file, it is
simply returned by the validate method. Although only briefly illustrated, the typical use case 
is given within a unit test in this project: 

<pre class="brush: java" title="Example ParseTree data (GraphContext)"><![CDATA[
    @Test
    public void validateOkDotFile() {

        // Assemble
        final InputStream unixesStream = getClass().getClassLoader().getResourceAsStream("testdata/unixes.dot");

        // Act
        final DotParser.GraphContext graphContext = DotDiagramValidator.validate(unixesStream);

        // Assert
        Assert.assertNotNull(graphContext);

        final TerminalNode digraph = graphContext.DIGRAPH();
        Assert.assertNotNull(digraph);

        final TerminalNode graph = graphContext.GRAPH();
        Assert.assertNull(graph);

        final TerminalNode strict = graphContext.STRICT();
        Assert.assertNull(strict);
    }
]]></pre>
