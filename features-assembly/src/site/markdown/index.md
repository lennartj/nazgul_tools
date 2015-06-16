# Feature Assembly

The nazgul-tools-feature-assembly creates and deploys a Karaf feature file which facilitates installing
all Nazgul Framework: Tools bundles into a Karaf runtime. To enable installing all nazgul tools bundles,
simply add the Nazgul Tools feature configuration to Karaf.

## Adding nazgul_tools feature from the command line

Launch karaf normally:

    ./bin/karaf
    
Then add the nazgul_tools feature:

    karaf@root()> feature:repo-add mvn:se.jguru.nazgul.tools.features/nazgul-tools-features-assembly/LATEST/xml/features

If you list the available features, the nazgul-tools feature is shown in the top of the listing. Mind that 
the version shown in your console is likely a stable version, unless you have built the Nazgul Framework: Tools from 
source and on a SNAPSHOT version.

    karaf@root()> feature:list
    Name                          | Version          | Installed | Repository               | Description
    --------------------------------------------------------------------------------------------------------------------------------------------
    logback-classic               | 4.0.3-SNAPSHOT   |           | nazgul-tools             | Nazgul Framework: Tools
    nazgul-tools                  | 4.0.3-SNAPSHOT   |           | nazgul-tools             | Nazgul Framework: Tools
    spring-dm                     | 1.2.1            |           | spring-3.0.3             | Spring DM support
    ...

The Nazgul Tools features are now available for installation. However, all Nazgul Framework projects use 
[Simple Logging Facade for Java (SLF4J)](http://www.slf4j.org) for logging. The Nazgul Framework does not define an 
explicit feature dependency to slf4j, since the standard package of Karaf normally exports pax-logging - which, in turn,
exports org.slf4j. Simply install the nazgul-tools bundle with the command

    karaf@root()> feature:install nazgul-tools
    
The feature will now be installed:
    
    karaf@root()> feature:list
    Name                          | Version          | Installed | Repository               | Description
    --------------------------------------------------------------------------------------------------------------------------------------------
    logback-classic               | 4.0.3-SNAPSHOT   |           | nazgul-tools             | Nazgul Framework: Tools
    nazgul-tools                  | 4.0.3-SNAPSHOT   | x         | nazgul-tools             | Nazgul Framework: Tools
    spring-dm                     | 1.2.1            |           | spring-3.0.3             | Spring DM support
    ...
    
## Making Karaf auto-launch the `nazgul-tools` feature

At boot time, Karaf reads the file `etc/org.apache.karaf.features.cfg` to find the feature repositories and features
which should be installed at boot time. If you want the nazgul-tools feature to be automatically installed into Karaf
at boot time, add the Nazgul Tools feature repository to the list of featureRepositories used by Karaf and the 
nazgul-tools feature identifier to the end of the featuresBoot option:  

simply add `nazgul-tools` to the `featuresBoot` option as shown below. The featureRepositories option is given on a 
single line in the configuration file but has been wrapped for readability in this documentation:
 
    #
    # Comma separated list of features repositories to register by default
    #
    featuresRepositories=mvn:org.apache.karaf.features/standard/3.0.3/xml/features,
        mvn:org.apache.karaf.features/enterprise/3.0.3/xml/features,
        mvn:org.ops4j.pax.web/pax-web-features/3.1.4/xml/features,
        mvn:org.apache.karaf.features/spring/3.0.3/xml/features,
        mvn:se.jguru.nazgul.tools.features/nazgul-tools-features-assembly/LATEST/xml/features
    
    #
    # Comma separated list of features to install at startup
    #
    featuresBoot=config,standard,region,package,kar,ssh,management,nazgul-tools
    
When launching Karaf, the nazgul-tools feature is now automatically installed:
    
    karaf@root()> feature:list
    Name                          | Version          | Installed | Repository               | Description
    --------------------------------------------------------------------------------------------------------------------------------------------
    logback-classic               | 4.0.3-SNAPSHOT   |           | nazgul-tools             | Nazgul Framework: Tools
    nazgul-tools                  | 4.0.3-SNAPSHOT   | x         | nazgul-tools             | Nazgul Framework: Tools
    spring-dm                     | 1.2.1            |           | spring-3.0.3             | Spring DM support
    ...

While the documentation was performed on a development (i.e. SNAPSHOT) version, your version of the
nazgul-tools-validation-aspect and its api should be a fixed version.

## Bundles activated by the nazgul-tools feature

The following bundles are made active by the nazgul-tools feature. Again, please note that the versions of the 
nazgul-tools-validation-api and nazgul-tools-validation-aspect bundles will be fix, release versions unless you
build a snapshot version yourself:

    karaf@root()> bundle:list
    START LEVEL 100 , List Threshold: 50
    ID | State  | Lvl | Version        | Name
    -------------------------------------------------------------------
    64 | Active |  50 | 4.0.3.SNAPSHOT | nazgul-tools-validation-api
    65 | Active |  50 | 4.0.3.SNAPSHOT | nazgul-tools-validation-aspect
    66 | Active |  50 | 3.4.0          | Apache Commons Lang
    67 | Active |  50 | 1.8.6          | AspectJ_Runtime
    
    