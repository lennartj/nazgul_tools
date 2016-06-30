# [1. Nazgul Framework: Tools](http://lennartj.github.io/nazgul_tools)

> "[Nazgul Tools](http://lennartj.github.io/nazgul_tools) helps me build stuff quicker and better."

The Nazgul Framework project holds a collection of best-pracises and sensible configurations enabling you to start
projects quickly and scale them considerably without having to change the development or deployment model.
Moreover, the Nazgul Framework strives to increase code quality, maintainability and usability for the developers
and architects working on a project, as well as reduce complexity/tanglement and increase productivity.

The Nazgul Framework consists of software components split between two reactors:

1. **[Nazgul Framework: Tools](https://github.com/lennartj/nazgul_tools)**. The Nazgul Tools project (this reactor)
    aims to use best-of-breed tools to achieve a usable, well-composed and simple mode of development and deployment.
    It defines codestyle, IDE integration, Maven plugins and a commonly usable way to automatically validate
    state for any class in any project.

2. **[Nazgul Framework: Core](https://github.com/lennartj/nazgul_core)**. The Nazgul Core project (another reactor)
    provides a set of library tools, built using the Nazgul Tools codestyle. These tools have small footprints
    provide well-defined tasks and are ready for immediate use in any project.

## 1.1. Release Documentation

Release documentation (including Maven site documentation) can be found
at [The Nazgul Framework: Tools Documentation Site](http://lennartj.github.io/nazgul_tools).
Select the release version you are interested in, to find its full Maven site documentation.

# 2. Getting and building nazgul_tools

The nazgul_tools is a normal Git-based Maven project, which is simple to clone and quick to build.

## 2.1. Getting the repository

Clone the repository, and fetch all tags:

```
git clone https://github.com/lennartj/nazgul_tools.git

cd nazgul_tools

git fetch --tags
```

## 2.2. Building the Nazgul Tools project

For the latest development build, simply run the build against the latest master branch revision:

```
mvn clean install
```

For a particular version, checkout its release tag and build normally:

```
git checkout nazgul-tools-4.1.1

mvn clean install
```

All tags (and hence also all release versions) are visible using the command

```
git tag -l
```

### 2.2.1. Building with different Maven versions

For building the project with another Maven version, simply run the following
script, where the `${MAVEN_VERSION}` should be substituted for a version number
such as `3.3.3`:

```
mvn -N io.takari:maven:wrapper -Dmaven=${MAVEN_VERSION}

./mvnw --show-version --errors --batch-mode validate dependency:go-offline

./mvnw --show-version --errors --batch-mode clean verify site
```

In the windows operating system, use `mvnw.bat` instead.