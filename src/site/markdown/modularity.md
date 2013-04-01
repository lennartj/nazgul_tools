# Modularity

"Modularity" means dividing complex software development into parts (modules), with the intent to simplify
development and maintenance. Presumably, all of us developers and software architects know of modularity
and realize that it is a Good Thing - in particular for large-scale enterprise development to avoid the
accumulation of complex, dependency-tangled and optionally untested code (a.k.a. "legacy code").

While modularity is fairly simple to define on a conceptual level, it can be somewhat more complex to design,
implement and enforce. The Nazgul project defines a battle-proven development model to simplify introduction
and use of modularity in (large-scale) software engineering projects. The Nazgul development model is matched
by a scalable and manageable deployment model; it is important to consider the runtime state from the start
of development.

Modularity yields advantages in two main groups:
1. *Software engineering improvements*. In plain english, modularity can help you structure your code and
   version control system (VCS) in a better way. Applied correctly, modularity can simplifiy learning a new
   codebase for new developers and lay the grounds for better deployment, scaling and monitoring.
2. *Runtime improvements*. To reach its potential, modularity must be enforced in the running JVM. While there
   have been some long-running efforts from major players in the Java area to provide a Java module system with
   general use, there are few contenders which can be used in existing JVMs today. Therefore,

## Software engineering modularity

It is important to solve a problem in the simplest way possible - *but no simpler than that*.
The Nazgul projects defines a codestyle which simplifies modularity for projects of *all* sizes,
implying that the modularity definitions in Nazgul Tools can be used unchanged even if projects grow considerably.

<img src="images/plantuml/modularity_mavenProjects.png" style="float:left; margin:10px; border: 1px solid black;" />
This implies that a module Apache Maven provides tools and means to create modularity for developers. Apache Maven
provides tools and means to create modularity for developers. Apache Maven provides tools and means to create
modularity for developers. Apache Maven provides tools and means to create modularity for developers.

## JVM runtime modularity

The Nazgul Framework provides fully OSGi-compliant projects, where model, API and SPI project types export all
packages as public, whereas the implementation projects hide all their classes
(i.e. having completely private packages).

This implies that [Nazgul Software Component ("NSC")](software_components.html) modularity can be enforced in all
modern JEE servers as well as within OSGi runtimes; the latter case enabling hot-redeployment of services to achieve
near-100% uptime for a running server.