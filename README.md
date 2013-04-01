# Nazgul Framework: Tools

<img src="src/site/resources/images/nazgul.jpg" style="float:right" width="203" height="236"/> The Nazgul Framework
project holds a collection of best-pracises and sensible configurations enabling you to start projects quickly and
scale them to huge sizes without having to change the development or deployment model. Moreover,
the Nazgul Framework strives to increase usability for the developers and architects working on a project,
as well as reduce complexity/tanglement and increase productivity.

> I am not young enough to know everything.
>  Oscar Wilde

## The purposes of the different projects are:

* **Codestyle**: contains enforcer rules and specification for build validator plugins such as
  checkstyle, pmd, cpd etc. 
* **Validation**: holds the validation API and its corresponding validation Aspect.
  These can be used throughout any implementation project.
* **Poms**: contains poms used outside of this build tools reactor.
  These poms are a reactor pom and an (external) parent pom, used by the projects outside this reactor.

### Site:stage for Nazgul Tools

Staging a site for the Nazgul Tools reactor with released versions of the maven-site-plugin is impossible until
[https://jira.codehaus.org/browse/MSITE-669](https://jira.codehaus.org/browse/MSITE-669)
is fixed. Meanwhile, an inofficial site plugin has been supplied within the the download section of the bitbucket
nazgul_tools area. Therefore - if you would like to build a locally staged site for the Nazgul Tools reactor,
proceed as follows:

1. First, download [maven-site-plugin-3.3.1.jguru] (pom and jar) from the download section-

2. Install them into your local maven repository using the command
   `mvn install:install-file -Dfile=maven-site-plugin-3.3.1.jguru.jar -DpomFile=maven-site-plugin-3.3.1.jguru.pom`.
   This custom/patched site plugin is required to build a staged site; it will be replaced with a released version of
   the maven-site-plugin when [https://jira.codehaus.org/browse/MSITE-669](https://jira.codehaus.org/browse/MSITE-669)
   is included into a release. Since the release frequency of the maven site plugin seems to be rather low,
   an intermediary/patched version is supplied here.

3. Clone the Nazgul Tools repository to your local harddrive.

4. Replace the versions for the maven-site-plugin within projects `nazgul-tools-root` and `nazgul-tools-parent` with
   the version of the custom site plugin (i.e. `3.3.1.jguru`)

5. Build the staged site using the standard `mvn site:stage`