# Nazgul Framework: Codestyle overview

<img src="images/codestyleStructure.png" style="float:right; margin:10px;" />
The codestyle project contains configuration for plugins embedded in the maven build, as well as IDEs intended
to be used for development. The configuration is roughly divided into four sections:

1. **Maven Enforcer Rules**. Implementation of enforcement rules used to ensure that the structure of
    Nazgul Software Components (NSC) is correctly created, including checking correctness of maven dependency
    imports in POM files. (For example, not permitting imports of in-reactor implementation projects into other
    implementation projects).

2. **IDE configuration**. Configuration documents used to quickly set up integrated development environments.
    Currently, support is only properly devised for IntelliJ IDEA, with alpha-leve setup support for Eclipse.
    While certainly not impossible to integrate, support for other IDEs is currently lacking. For a walkthrough
    of configuring your IDE for simple development with the Nazgul Framework, refer to the
    correct IDE link in the setup instructions below.

3. **License text configuration**. License texts used within the project are kept in section three of the
    codestyle project. These texts are used to configure the
    [Maven License Plugin](http://mojo.codehaus.org/license-maven-plugin/) for use within the Nazgul build reactor.

4. **Other plugin configurations**. Checkstyle, PMD and findbugs are used within the build reactor of all Nazgul
    projects. In contrast to the IDE configuration, which sets up the codestyle definitions for immediate use within
    an IDE, the plugin definitions found in this section can be used by builds within a [Continuous Integration
    server](http://en.wikipedia.org/wiki/Continuous_integration) server such as Jenkins.

## Setting up your IDE

The Nazgul Framework provides a setup for Maven, including several plugins which check licensing, codestyle,
pom properties etc. Most developers, however, spend the majority of their development time within the Integrated
Development Environment. Providing a configuration for the IDE which matches the plugin configuration
for Maven's build cycle greatly simplifies the job of focusing on development instead of setup.

Unfortunately, most IDEs have custom and incompatible setup configurations for the various steps that handle
code formatting, project imports etc. Each IDE has a slightly different way to perform setup:

<table>
    <tr>
        <th width="50%">Integrated Development Environment Setup Link</th>
        <th width="50%">Setup Compliance Status</th>
    </tr>
    <tr>
        <td><a href="setup/idea.html">IntelliJ IDEA 12.x</a></td>
        <td>Tested OK</td>
    </tr>
    <tr>
        <td><a href="setup/eclipse.html">Eclipse Kepler</a></td>
        <td>Unverified</td>
    </tr>
    <tr>
        <td><a href="setup/netbeans.html">Netbeans 7.3+</a></td>
        <td>Unverified</td>
    </tr>
</table>