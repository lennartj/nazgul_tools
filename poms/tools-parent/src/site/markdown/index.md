# Nazgul Tools Parent - overview

The nazgul-tools-parent project holds a single pom, defining most of the Nazgul-style build cycle including
various maven plugin definitions.

**Note!** Do **not** use the nazgul-tools-parent pom as a direct parent. Instead:

1.  Use **nazgul-tools-external-parent** as parent for projects defining artifacts (i.e. all projects except
    reactor pom projects).

2.  Use **nazgul-tools-external-reactor** as parent for reactor pom projects.

## Nazgul tools reactor structure

The nazgul tools reactor is structured as shown in the image below.
Note the external poms, intended for use in a child reactor.

<img src="images/plantuml/nazgul_tools.png" style="margin:10px;" />