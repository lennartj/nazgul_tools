Nazgul Framework tools
======================

This project contains the commonly used build tools and the commonly
available Validation API and Validation Aspect.

Simply clone the repository and build using 

**mvn clean install**

The purposes of the different projects are:
---------------------

* **Codestyle**: contains enforcer rules and specification for build validator plugins such as
  checkstyle, pmd, cpd etc. 
* **Validation**: holds the validation API and its corresponding validation Aspect.
  These can be used throughout any implementation project.
* **Poms**: contains poms used outside of this build tools reactor.
  These poms are a reactor pom and an (external) parent pom, used by the projects outside this reactor.