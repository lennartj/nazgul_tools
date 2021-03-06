# Nazgul Tools: Validation Aspect

The Validation Aspect project contains the Aspect implementation for performing automatic callbacks to Validatable
objects. It is included automatically within the `nazgul-tools-external-parent` pom, implying
that all poms using said parent within its parent chain will acquire Validation on all its Validatable objects.

Since the Validatable pattern is realized using aspects, no particular runtime environment is required for its
execution. In particular, no Dependency Injection framework or Application Server container is required for its proper
operation (although it works equally well inside DI frameworks or Application Server containers).