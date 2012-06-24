Nazgul Framework tools
======================

This project contains the commonly used build tools and the commonly
available Validation API and Validation Aspect.

Simply clone the repository and build using 

<code>mvn clean install</code>

The purposes of the different projects are:
---------------------

* <strong>Codestyle</strong>: contains enforcer rules and specification for build validator plugins such as 
  checkstyle, pmd, cpd etc. 
* <strong>Validation</strong>: holds the validation API and its corresponding validation Aspect.
  These can be used throughout any implementation project.
* <strong>Poms</strong>: contains poms used outside of this build tools reactor.
  These poms are a reactor pom and an (external) parent pom, used by the projects outside this reactor.