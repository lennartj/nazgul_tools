# Reducing software complexity

<img src="../images/plantuml/tanglements_reduction.png" style="float:right; margin:10px;" />
Assuming that the complexity of a codebase is proportional to its size, large-scale
development requires some means to reduce the mass of imported dependencies.
A simple yet successful way of achieving dependency reduction is to divide development
into [Nazgul Software Component ("NSC")](../software_components.html) projects - i.e.
separate APIs from implementations.

The image to the right illustrates that a client declaring a depency to a module
containing both API and implementation types is encumbered with the transitive
dependencies of both.
Separating the API and Implementation types into different projects reduces coupling
for the client, since none of the implementation types or their transitive dependencies
will be seen from the client. This is important, since implementations frequently depend on
a larger number of transitive dependencies than APIs (which are typically slimmer in nature
than implementations).

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