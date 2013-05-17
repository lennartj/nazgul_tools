# Reducing software complexity

Assuming that the complexity of a codebase is proportional to its size, large-scale
development requires some means to reduce the mass of imported dependencies in order to
reduce its complexity. Reducing complexity - particularly in large corporate development
projects - is necessary to preserve a quicker development/release pace.

A simple yet successful way of achieving dependency reduction is to divide development
into [Nazgul Software Component ("NSC")](../software_components.html) projects - i.e.
separate APIs from implementations.

Separating the API and Implementation types into different projects reduces coupling
for the client, since none of the implementation types or their transitive dependencies
will be seen from the client. This is important, since implementations frequently depend on
a larger number of transitive dependencies than APIs (which are typically slimmer in nature
than implementations).

## An illustrated dependency example

The image below illustrates the dependency tanglement forced onto a client which needs to invoke
the `someMethod()` in an interface defined within the "All-In-One" project. The client project
will need to include a maven dependency in its pom to the "All-In-One" project containing the ApiType:

    <dependency>
        <groupId>the.groupid</groupId>
        <artifactId>All-In-One</artifactId>
        <version>1.0.0</version>
    </dependency>

Given that the All-In-One project contains both the interface specification and an implementation,
the transitive dependencies from the All-In-One project must also be included in the build.
As illustrated in the image below, we assume that the ApiType uses a dependency "2a. Api Dependencies",
and that the implementation also contained within the All-In-One project uses dependencies from the
"2b. Impl Dependencies" projects.

As always, all dependencies of a project must be available on the classpath for the build to succeed.
This implies that the compile-time dependencies required to build the project of the "Calling client"
includes all the types included within the yellow-ish rectangle "3. Compile-time Dependencies for API usage".
In this case, the scope is identical to the scope at runtime, which means that the full implementation
must be present just to compile the project containing "Calling client". Also, the runtime scope (indicated
by "4. Run-time Dependencies") contains all projects in the implementation, even if the implementation is not used
(say, by injecting another implementation of the ApiType at runtime).

This scenario increases the risk of "jar-hell"-like problems unless the runtime loads dependencies
with separate classloaders, such as an OSGi solution.
And even worse - this is simply **bad development practise**.

<img src="../images/plantuml/tanglements_reduction.png" style="margin:10px;" />

Instead, we could decouple the implementation from the API by placing it in a separate maven project.
This implies that unwanted dependency bloat is reduced considerably, as illustrated in the image below.
Note now, that the "3. Compile-time Dependencies for API usage" set differs from the "4. Run-time Dependencies".
This is a Good Thing, since it reduces dependency tanglement. [Nazgul Software Component ("NSC")]
(../software_components.html) projects adhere to this principle of separating API projects from implementation
projects.

<img src="../images/plantuml/tanglements_reduction_noimpl.png" style="margin:10px;" />

## Definitions: Coupling and Cohesion

Parts of software complexity are defined by the coupling and cohesion of the codebase.

1. **Cohesion**: the degree to which the elements of a module belong together.

2. **Coupling**: the degree to which each program module relies on each one of the other modules.

Some researchers consider coupling and cohesion to be difficult or impossible to separate.
One example of this view is hinted at by the article, whose abstract is shown below:

### [The Structural Complexity of Software: An Experimental Test](http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=1556556&url=http%3A%2F%2Fieeexplore.ieee.org%2Fxpls%2Fabs_all.jsp%3Farnumber%3D1556556)

Article published in *IEEE Transactions on Software Engineering* (Nov. 2005) by
authors David P. Darcy, Chris F. Kemerer, Sandra A. Slaughter and James E. Tomayko.

> This research examines the structural complexity of software and, specifically, the potential interaction of the
> two dominant dimensions of structural complexity, coupling and cohesion. Analysis based on an information
> processing view of developer cognition results in a theoretically driven model with cohesion as a moderator for
> a main effect of coupling on effort.
> An empirical test of the model was devised in a software maintenance
> context utilizing both procedural and object-oriented tasks, with professional software engineers as participants.
> The results support the model in that there was a significant interaction effect between coupling and cohesion
> on effort, even though there was no main effect for either coupling or cohesion.
> The implication of this result is that, when designing, implementing, and
> maintaining software to control complexity, both coupling and cohesion
> should be considered jointly, instead of independently.
> By providing guidance on structuring software for software professionals and
> researchers, these results enable software to continue as the solution of choice for a wider
> range of richer, more complex problems.