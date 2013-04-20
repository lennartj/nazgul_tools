# Nazgul Framework: Codestyle overview

<img src="images/codestyleStructure.png" style="float:right; margin:10px;" />
The codestyle project contains configuration for plugins embedded in the maven build, as well as IDEs intended
to be used for development. The configuration is roughly divided into four sections:

1. **Maven Enforcer Rules**. Implementation of enforcement rules used to ensure that the structure of
    Nazgul Software Components (NSC) is correctly created, including checking correctness of maven dependency
    imports in POM files. (For example, not permitting imports of in-reactor implementation projects into other
    implementation projects).

2. **IDE confiugration**. Configuration documents used to quickly set up integrated development environments.
    Currently, support is only properly devised for IntelliJ IDEA, with alpha-leve setup support for Eclipse.
    While certainly not impossible to integrate, support for other IDEs is currently lacking. For a walkthrough
    of configuring your IDE for simple development with the Nazgul Framework, refer to the
    [IDE Setup](setup/ide_setup.html) page.

3. **License text configuration**. License texts used within the project are kept in section three of the
    codestyle project. These texts are used to configure the
    [Maven License Plugin](http://mojo.codehaus.org/license-maven-plugin/) for use within the Nazgul build reactor.

4. **Other plugin configurations**. Checkstyle, PMD and findbugs are used within the build reactor of all Nazgul
    projects. In contrast to the IDE configuration, which sets up the codestyle definitions for immediate use within
    an IDE, the plugin definitions found in this section can be used by builds within a [Continuous Integration
    server](http://en.wikipedia.org/wiki/Continuous_integration) server such as Jenkins.