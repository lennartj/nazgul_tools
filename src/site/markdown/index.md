# What is the Nazgul Framework?

<img src="images/nazgul.jpg" style="float:right" width="203" height="236"/> 
Software complexity costs - in reduced velocity and increased costs for onboarding and confusion.
It is therefore vital to reduce complexity as much as possible - and this can be done for small to really large 
codebases by using the Nazgul Framework to structure the codebase.
 
To this end, the Nazgul Framework project holds a collection of best-pracises and sensible configurations enabling you
to start projects quickly and scale them to huge sizes without having to change the development or deployment model. 
Moreover, the Nazgul Framework strives to increase usability for the developers and architects working on a project, 
as well as reduce complexity/tanglement and increase productivity.

The Nazgul Framework has two main components:

1. **Nazgul Framework: Tools**. The Nazgul Tools project (this reactor) aims to use best-of-breed tools to achieve a
    usable, well-composed and simple mode of development and deployment. It defines Maven plugins, Codestyle and
    Quality tracking, IDE integration and global aspects usable in any project development. Nazgul Tools builds fully
    OSGi-compliant artifacts and a features-assembly which can be used directly within an OSGi runtime - or as part 
    of any general Java development.

2. **Nazgul Framework: Core**. The Nazgul Core project (another reactor) provides a set of commonly useable library
    tools, built on top of the Nazgul Tools codestyle, IDE integration and global aspects. These tools are mainly
    self-sustaining and are ready to be integrated and used in any project. Nazgul Core builds fully
    OSGi-compliant artifacts and a features-assembly.

## What can the Nazgul Framework do for my project?

In short: Increase your productivity, raise your server uptime and provide scalable projects.
Using the Nazgul Framework lets you focus on development while deployment, scalability and reduced tanglement are
inherited from the choices within the Nazgul Framework.

## How should I proceed?

Want to get started with your Nazgul Tools-derived project?

1. Check out the [concepts](concepts.html).

1. Read through the thoughs and structure of [Nazgul Software Components](nazgul_tools.html).

1. Read [a brief overview of the Nazgul Tools reactor](nazgul_tools.html).

1. Import some components from Nazgul Framework: Core for direct use - or use the `nazgul-tools-external-parent` as a
   parent within your development project as shown in [Nazgul Software Components](nazgul_tools.html).

... and we are working on a few screencasts, to shorten the startup time even more...