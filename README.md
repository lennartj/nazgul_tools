Nazgul Framework tools
======================

This project contains the commonly used build tools and the commonly
available Validation API and Validation Aspect.

First, download [maven-site-plugin-3.3.1.jguru] (pom and jar) from the
download section and install them into your local maven repository using the
command:

**mvn install:install-file -Dfile=maven-site-plugin-3.3.1.jguru.jar -DpomFile=maven-site-plugin-3.3.1.jguru.pom**

This custom/patched site plugin is required to build a staged site; it will be replaced with a
released version of the maven-site-plugin when http://jira.codehaus.org/browse/MSITE-669 is included
into a release. Since the release frequency of the maven site plugin seems to be rather low,
an intermediary/patched version is supplied here.

Next, simply clone the repository and build using

**mvn clean install**

The purposes of the different projects are:
---------------------

* **Codestyle**: contains enforcer rules and specification for build validator plugins such as
  checkstyle, pmd, cpd etc. 
* **Validation**: holds the validation API and its corresponding validation Aspect.
  These can be used throughout any implementation project.
* **Poms**: contains poms used outside of this build tools reactor.
  These poms are a reactor pom and an (external) parent pom, used by the projects outside this reactor.