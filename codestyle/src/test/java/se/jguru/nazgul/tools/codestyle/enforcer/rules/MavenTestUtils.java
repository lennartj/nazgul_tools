/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2015 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *        http://www.jguru.se/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.codestyle.enforcer.rules;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public final class MavenTestUtils {

    /**
     * The default Stub version.
     */
    public static final String DEFAULT_VERSION = "1.0.0";

    private MavenTestUtils() {
    }

    /**
     * Reads the POM found at the supplied filePath, retrieving the created MavenProject.
     *
     * @param filePath The path to the pom.xml file.
     * @return The MavenProject created from the filePath pom.
     */
    public static MavenProject readPom(final String filePath) {

        try {
            final URL pomResource = MavenTestUtils.class.getClassLoader().getResource(filePath);
            /*

            Validate.notNull(pomResource, "Could not find pom for filePath [" + filePath + "]");

            final DefaultMavenProjectBuilder builder = new DefaultMavenProjectBuilder();
            builder.initialize();

            final DefaultProjectBuilderConfiguration builderConfiguration = new DefaultProjectBuilderConfiguration();

            return builder.build(new File(pomResource.getPath()), builderConfiguration);
            */

            final Reader pomReader = new InputStreamReader(pomResource.openStream());
            return new MavenProject(new MavenXpp3Reader().read(pomReader));

        } catch (Exception e) {
            throw new IllegalArgumentException("Could not read pom from [" + filePath + "]", e);
        }
    }

    /**
     * Creates a MavenProject from the supplied data, and using version 1.0.0.
     *
     * @param packaging  The packaging for the MavenProject to return.
     * @param groupId    The groupId for the MavenProject to return.
     * @param artifactId The artifactId for the MavenProject to return.
     * @return a MavenProjectStub created from the supplied properties.
     */
    public static MavenProject getStub(final String packaging,
                                       final String groupId,
                                       final String artifactId) {
        // Delegate
        return getStub(packaging, groupId, artifactId, DEFAULT_VERSION);
    }

    /**
     * Creates a MavenProject from the supplied data.
     *
     * @param packaging  The packaging for the MavenProject to return.
     * @param groupId    The groupId for the MavenProject to return.
     * @param artifactId The artifactId for the MavenProject to return.
     * @param version    The version for the MavenProject to return.
     * @return a MavenProjectStub created from the supplied properties.
     */
    public static MavenProject getStub(final String packaging,
                                       final String groupId,
                                       final String artifactId,
                                       final String version) {

        final Model model = new Model();
        model.setModelVersion("4.0.0");
        model.setGroupId(groupId);
        model.setArtifactId(artifactId);
        model.setVersion(version);
        model.setPackaging(packaging);

        // The MavenProjectStub does not query its model for GAV values ...
        final MavenProject toReturn = new MavenProject(model);
        toReturn.setGroupId(groupId);
        toReturn.setArtifactId(artifactId);
        toReturn.setPackaging(packaging);
        toReturn.setVersion(version);
        return toReturn;
    }
}
