# Nazgul Tools: Visualization Doclet Implementation

The Visualization Doclet implementation merges the Visualization DOT SPI with the Javadoc Doclet specification, 
creating a Doclet which injects class diagrams into JavaDoc package and class diagrams.

## 1. Install the GraphViz application for your OS

It is important to realize that the actual rendering of class diagrams as images images is not done by the doclet 
itself, but instead by the "dot" executable within the [GraphViz](https://graphviz.gitlab.io) application. Graphvis 
is an open-source application using the Common Public License Version 1.0. Used, however, as an independent 
installation on your system, its licensing does not affect the license of your product.

The visualization doclet finds Graphviz and its binary executables from querying 2 properties in order.

1. Java system property `graphviz.home`. If set, the Doclet assumes that this property contains the absolute path to 
   the Graphviz installation directory. 
2. The environment property `GRAPHVIZ_HOME`. If set, the Doclet assumes that this property contains the absolute 
   path to the Graphviz installation directory. 
3. If none of these properties are set, the Doclet will use the standard system path to find GraphViz. 

Should the Doclet not be able to find GraphViz, it will complain on the `System.err` stream letting the developer 
know that the GraphViz binary could not be found. The code snippet below shows the actual constant definitions of 
the GraphViz finding properties:   

        /**
         * The System property key defining the graphviz home. If the result of
         * {@code System.getProperty(HOMEDIR_SYSTEM_PROPERTY)} returns a non-null value, it is assumed to be
         * the absolute path to the installation directory of Graphviz.
         */
        public static final String HOMEDIR_SYSTEM_PROPERTY = "graphviz.home";
        
        /**
         * The environment key defining the graphviz home. If the result of
         * {@code System.getenv(HOMEDIR_ENV_PROPERTY)} returns a non-null value, it is assumed to be
         * the absolute path to the installation directory of Graphviz.
         */   
        public static final String HOMEDIR_ENV_PROPERTY = "GRAPHVIZ_HOME";

## 2. Configure the JavaDoc plugin to use the Visualization Doclet

The configuration section of the `maven-javadoc-plugin` accepts two parameters, 
donning information about the doclet class and the maven artifact wherein the 
doclet class is found. Those two settings are shown in isolation below:

    <doclet>se.jguru.nazgul.tools.visualization.impl.doclet.DelegatingDoclet</doclet>
    <docletArtifact>
        <groupId>se.jguru.nazgul.tools.visualization.impl.doclet</groupId>
        <artifactId>nazgul-tools-visualization-impl-doclet</artifactId>
        <version>${nazgul-tools.version}</version>
    </docletArtifact>

A full-blown maven-javadoc-plugin configuration example with the doclet and docletArtifact elements present 
is shown below: 

     <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>${maven-javadoc-plugin.version}</version>
        <configuration>
            <doclet>se.jguru.nazgul.tools.visualization.impl.doclet.DelegatingDoclet</doclet>
            <docletArtifact>
                <groupId>se.jguru.nazgul.tools.visualization.impl.doclet</groupId>
                <artifactId>nazgul-tools-visualization-impl-doclet</artifactId>
                <version>${nazgul-tools.version}</version>
            </docletArtifact>
            <failOnError>false</failOnError>
            <stylesheet>java</stylesheet>
            <groups>
                <group>
                    <title>Nazgul Framework: Tools Packages</title>
                    <packages>se.jguru.nazgul.tools.*</packages>
                </group>
                <group>
                    <title>Nazgul Framework: Core Packages</title>
                    <packages>se.jguru.nazgul.core.*</packages>
                </group>
                <group>
                    <title>Nazgul Framework: Test Artifact Packages</title>
                    <packages>se.jguru.nazgul.test.*</packages>
                </group>
            </groups>
            <useStandardDocletOptions>true</useStandardDocletOptions>
            <charset>${project.build.sourceEncoding}</charset>
            <encoding>${project.build.sourceEncoding}</encoding>
            <docencoding>${project.build.sourceEncoding}</docencoding>
            <breakiterator>true</breakiterator>
            <version>true</version>
            <author>true</author>
            <keywords>true</keywords>
            <additionalparam>-Xdoclint:none</additionalparam>
        </configuration>
     </plugin>  
     
The `groups` element (and children) should be configured for your project's purposes.