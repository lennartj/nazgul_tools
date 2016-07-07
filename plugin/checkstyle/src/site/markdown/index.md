# Nazgul Framework: Tools Checkstyle Plugin

The Nazgul Tools: Checkstyle plugin is a fork of the [Maven Checkstyle Plugin](https://github.com/apache/maven-plugins)
and should be used only until the
[bug which causes the checkstyle plugin to crash](https://github.com/apache/maven-plugins/pull/88) is merged into the
codebase, followed by a new release of the standard Maven Checkstyle Plugin.

In the meantime, the Nazgul Tools Checkstyle plugin can be used in the following manner:

<pre class="brush: xml" title="Example POM parent structure."><![CDATA[
                <plugin>
                    <groupId>se.jguru.nazgul.tools.plugin.checkstyle</groupId>
                    <artifactId>nazgul-tools-checkstyle-maven-plugin</artifactId>
                    <version>5.0.2-SNAPSHOT</version>
                    <configuration>
                        <skip>${skip.verify.code}</skip>
                        <configLocation>${checkstyle.config.location}</configLocation>
                        <suppressionsFile>${checkstyle.suppressions.location}</suppressionsFile>
                        <failOnViolation>${breakOnFailure.checkstyle}</failOnViolation>
                        <violationSeverity>${checkstyle.violationSeverity}</violationSeverity>
                    </configuration>
                    <executions>
                        <execution>
                            <id>verify</id>
                            <phase>${check.plugins.phase}</phase>
                            <goals>
                                <goal>check</goal>
                            </goals>
                        </execution>
                        <execution>
                            <id>report</id>
                            <goals>
                                <goal>checkstyle</goal>
                            </goals>
                        </execution>
                    </executions>
                    <dependencies>
                        <dependency>
                            <groupId>se.jguru.nazgul.tools.codestyle</groupId>
                            <artifactId>nazgul-codestyle</artifactId>
                            <version>5.0.2-SNAPSHOT</version>
                        </dependency>
                        <!--
                            Workaround https://github.com/jcgay/maven-color/issues/8
                            i.e. Maven 2 to 3 incompatibilities.
                         -->
                        <dependency>
                            <groupId>org.slf4j</groupId>
                            <artifactId>jcl-over-slf4j</artifactId>
                            <version>${slf4j.version}</version>
                        </dependency>
                        <dependency>
                            <groupId>org.slf4j</groupId>
                            <artifactId>slf4j-jdk14</artifactId>
                            <version>${slf4j.version}</version>
                        </dependency>
                    </dependencies>
                </plugin>
]]></pre>