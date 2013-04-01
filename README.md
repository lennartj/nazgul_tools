# Nazgul Framework: Tools

The Nazgul Framework project holds a collection of best-pracises and sensible configurations enabling you to start
projects quickly and scale them considerably without having to change the development or deployment model. Moreover,
the Nazgul Framework strives to increase usability for the developers and architects working on a project,
as well as reduce complexity/tanglement and increase productivity.

The Nazgul Framework has two main components:

1. **Nazgul Framework: Tools**. The Nazgul Tools project (this reactor) aims to use best-of-breed tools to achieve a
    usable, well-composed and simple mode of development and deployment. It defines codestyle,
    IDE integration and global aspects usable in any project development.

2. **Nazgul Framework: Core**. The Nazgul Core project (another reactor) provides a set of commonly useable library
    tools, built on top of the Nazgul Tools codestyle, IDE integration and global aspects. These tools are mainly
    self-sustaining and are ready to be integrated and used in any project.

## Documentation

Main documentation found at:

[Main documentation](https://bytebucket.org/lennartj/nazgul_tools/wiki/index.html "Nazgul Framework: Tools docs")

#### Note!

Creating a proper staged site for the Nazgul Tools reactor (using the maven-site-plugin's standard site:stage goal)
is impossible until
[http://jira.codehaus.org/browse/MSITE-669](http://jira.codehaus.org/browse/MSITE-669)
is fixed. A custom/patched site plugin is currently required to build a staged site; it will be replaced with a
released version of the maven-site-plugin when http://jira.codehaus.org/browse/MSITE-669 is included
into a release.

Use the following approach to enable :

1. Download [maven-site-plugin-3.3.1.jguru] (pom and jar) from the download section

2. Install the custom site plugin into your local maven repository using the command
   `mvn install:install-file -Dfile=maven-site-plugin-3.3.1.jguru.jar -DpomFile=maven-site-plugin-3.3.1.jguru.pom`

3. Change the version for the maven-site-plugin to `3.3.1.jguru` in projects `nazgul-tools-root`,
   `nazgul-tools-parent` and `nazgul-codestyle`.

4. Fire `mvn site:stage` in the root directory of the project.