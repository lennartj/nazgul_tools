# Nazgul Framework: Quickstart

There are few pre-requisites to getting started with Nazgul Framework-style project development.

## Required software platform

There are 3 pieces of software which you must have installed to be able to build Nazgul Framework-style
projects:

1. An updated [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html),
   which should be installed in a directory without whitespace in its path to reduce the potential for
   build problems. Please follow the instructions on the Java site regarding its installation process.
2. A recent (3.0.5+) [Maven](http://maven.apache.org/download.cgi) installation, which should also be installed
   in a directory without whitespace in its path to reduce the potential for build problems. Please follow the
   instructions on the Maven site regarding its installation process.
3. A [graphviz](http://www.graphviz.org/Download.php) installation, which is used to generate
   [PlantUML diagrams](http://plantuml.sourceforge.net/). These occur frequently within the documentation of
   Nazgul Framework projects. Please follow the instructions on the Graphviz site regarding its installation process.

## Strongly recommended software

Setting up Nazgul-style projects and software components are made considerably quicker with a templating tool,
such as [Maven Archetype Plugin](http://maven.apache.org/archetype/maven-archetype-plugin) or
[JBoss Forge](http://forge.jboss.org/index.html).

The Nazgul Framework uses JBoss Forge, which should be downloaded and installed onto your local workstation
prior to launching the step-by-step walkthrough below. The Nazgul Framework facets and plugins are provided
within a Github-based project (["Nazgul Forge"](https://github.com/lennartj/nazgul_forge)), as is customary
for JBoss Forge plugins. Feel free to familiarize yourself with its codebase.

### 0. Download/install Required software

Please follow instructions on the [JBoss Forge](http://forge.jboss.org/index.html) site
for the installation of JBoss Forge itself.

### 1. Updating JBoss Forge

If you have an existing forge installation, simply update it with the command:

    forge update

In case there is a newer JBoss Forge version available, forge will respond with something similar to
the dialog steps below:

    ***INFO*** This Forge installation will be updated to 1.3.3.Final
     ? Is that ok ? [Y/n]

    Update in progress. Please wait... |
    ***SUCCESS*** Forge will now restart to complete the update...

Following a restart of JBoss Forge, the version is indeed updated to the latest available:

        _____
       |  ___|__  _ __ __ _  ___
       | |_ / _ \| `__/ _` |/ _ \  \\
       |  _| (_) | | | (_| |  __/  //
       |_|  \___/|_|  \__, |\___|
                       |___/

    JBoss Forge, version [ 1.3.3.Final ] - JBoss, by Red Hat, Inc. [ http://forge.jboss.org ]
    [no project] Nazgul $

### 2. Installing the Nazgul Forge plugins

You need to make the Nazgul Forge plugins and facets available to your forge installation.
When the Nazgul Forge plugins are accepted into JBoss Forge's Central Plugin Index (CPI),
this is simply done by launching Forge and installing the plugin, [as shown in the JBoss Forge
documentation](http://forge.jboss.org/docs/using/installing-new-plugins.html#content):

    forge find-plugin nazgul

Following a brief selection of which JBoss Forge plugin you want to install,
the download and installation into JBoss Forge should be automagic.

#### 2b. Source installation - in case you need to

You can clone [its GitHub repository](https://github.com/lennartj/nazgul_forge.git) and perform
a local installation if the Forge's resolution for finding the Nazgul Forge plugins fails for whatever reason.
This involves cloning the project from Git, building the project with maven and
finally launching forge within the factory-impl-nazgul project. The commands are shown below without their
respective output:

    git clone https://github.com/lennartj/nazgul_forge.git

    cd nazgul_forge

    mvn clean install

    cd factory/factory-impl-nazgul

    forge

Having launched forge, you can install the nazgul forge plugin with the following command
executed within JBoss Forge:

    forge source-plugin .

Forge will re-build the factory-impl-nazgul project, and install the Forge Plugins found
within it:

        _____
       |  ___|__  _ __ __ _  ___
       | |_ / _ \| `__/ _` |/ _ \  \\
       |  _| (_) | | | (_| |  __/  //
       |_|  \___/|_|  \__, |\___|
                       |___/

    JBoss Forge, version [ 1.3.3.Final ] - JBoss, by Red Hat, Inc. [ http://forge.jboss.org ]
    The following plugins have been activated: [add-nazgul-software-component, new-nazgul-project]

You are now all set to start creating your Nazgul Framework-style projects and components.

### 3. Create a new Nazgul Framework-style project

Following the installation of the nazgul plugin, you may create a new Nazgul Framework-style
project by firing the command `new-nazgul-project` as shown in the listing below. The command
primes a new directory (the "projectName" option) with the parent and reactor parent poms:

	[no project] tmp $ new-nazgul-project --projectName foobar --topProjectPackage com.acme

     ? Where would you like to create the project? [Press ENTER to use the current directory: tmp] [ .../tmp]

    What version do you want to install?

      1 - [se.jguru.nazgul.tools.poms.external:nazgul-tools-external-reactor-parent:::2.0.8]
      2 - [se.jguru.nazgul.tools.poms.external:nazgul-tools-external-reactor-parent:::2.0.9]

     ? Choose an option by typing the number of the selection: 2

    ***SUCCESS*** Created project [foobar] structure in new working directory [/tmp/foobar]
    Wrote /tmp/foobar
    Wrote /tmp/foobar/pom.xml
    Wrote /tmp/foobar/poms
    Wrote /tmp/foobar/poms/pom.xml
    Wrote /tmp/foobar/poms/foobar-parent
    Wrote /tmp/foobar/poms/foobar-parent/pom.xml
    Wrote /tmp/foobar/poms/foobar-api-parent
    Wrote /tmp/foobar/poms/foobar-api-parent/pom.xml
    Wrote /tmp/foobar/poms/foobar-model-parent
    Wrote /tmp/foobar/poms/foobar-model-parent/pom.xml
    [foobar-reactor] foobar $

Just type `ls -l` to see what was created for you:

    [foobar-reactor] foobar $ ls -l
    total 1
    -rw------- owner  users  1830 jul 31 21:26 pom.xml
    drwx------ owner  users   204 jul 31 21:26 poms/

As indicated above, the new-nazgul-project command might ask which version of various Nazgul parent poms you
would like to use. When asked, it is recommended to choose the lastest stable release possible.
You can now open the root pom.xml in your favourite IDE.

### 4. Add a new Nazgul Software Component (NSC)

This section assumes that your current working directory is inside a Nazgul 
Framework-style project, as described in step 3 above.
The nazgul plugin you installed can be used to create a NSC. For assistance,
simply type `help add-nazgul-software-component` to receive the online help
describing all available properties.

To create a "messaging" NSC with a model project and a "jms" implementation, fire the command:

    [foobar-reactor] foobar $ add-nazgul-software-component --named messaging --hasModelProject true --implType jms
    ***SUCCESS*** Created project [foobar] structure in new working directory [/tmp/foobar/messaging]
    Wrote /tmp/foobar/pom.xml
    Wrote /tmp/foobar/messaging
    Wrote /tmp/foobar/messaging/pom.xml
    Wrote /tmp/foobar/messaging/messaging-model
    Wrote /tmp/foobar/messaging/messaging-model/pom.xml
    Wrote /tmp/foobar/messaging/messaging-api
    Wrote /tmp/foobar/messaging/messaging-api/pom.xml
    Wrote /tmp/foobar/messaging/messaging-impl-jms
    Wrote /tmp/foobar/messaging/messaging-impl-jms/pom.xml
    [foobar-messaging-reactor] messaging $

Note that Forge changed its current directory to the reactor directory of the newly created messaging
NSC. While the listing emits some files and directory names, quite a few others (including skeleton package
directories and some test-scope logging definitions) are created as well. Feel free to take a look in your
favourite IDE.