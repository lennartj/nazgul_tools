# Nazgul Framework: Quickstart

Setting up Nazgul-style projects and software components are made considerably quicker with a templating tool,
such as [Maven Archetype Plugin](http://maven.apache.org/archetype/maven-archetype-plugin) or
[JBoss Forge](http://forge.jboss.org/index.html).

The Nazgul Framework uses JBoss Forge, which should be downloaded and installed onto your local workstation
prior to launching the step-by-step walkthrough below. The Nazgul Framework facets and plugins are provided
within a Github-based project (["Nazgul Forge"](https://github.com/lennartj/nazgul_forge)), as is customary
for JBoss Forge plugins. Feel free to familiarize yourself with its codebase.

### 0. Download/install JBoss Forge

Please follow instructions on the [JBoss Forge](http://forge.jboss.org/index.html) site
for the installation of JBoss Forge itself.

### 1. Updating JBoss Forge

If you have an existing forge installation, simply update it with the command:

    forge update

In case there is a newer JBoss Forge version available, forge will respond with something similar to
the dialog steps below:

    ***INFO*** This Forge installation will be updated to 1.3.1.Final
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

    JBoss Forge, version [ 1.3.1.Final ] - JBoss, by Red Hat, Inc. [ http://forge.jboss.org ]
    [no project] Nazgul $

### 2. Installing the Nazgul Forge plugins

You need to make the Nazgul Forge plugins and facets available to your forge installation.
When the Nazgul Forge plugins are accepted into JBoss Forge's Central Plugin Index (CPI),
this is simply done by launching Forge and installing the plugin, [as shown in the JBoss Forge
documentation](http://forge.jboss.org/docs/using/installing-new-plugins.html#content):

    forge find-plugin nazgul

Following a brief selection of which JBoss Forge plugin you want to install,
the download and installation into JBoss Forge should be automagic.

### 3. Create a new Nazgul Framework-style project

Description pending.

### 4. Create a new Nazgul Software Component (NSC)

This section assumes that you reside within a Nazgul Framework-style project, as described
in step 3 above.

Description pending.

### 5. Convert an existing project to a Nazgul Framework-style project

Description pending.