/*-
 * #%L
 * Nazgul Project: nazgul-tools-checkstyle-maven-plugin
 * %%
 * Copyright (C) 2010 - 2017 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *       http://www.jguru.se/licenses/jguruCorporateSourceLicense-2.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package se.jguru.nazgul.tools.plugin.checkstyle.rss;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.reporting.MavenReportException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.velocity.VelocityComponent;
import se.jguru.nazgul.tools.plugin.checkstyle.CheckstyleReport;
import se.jguru.nazgul.tools.plugin.checkstyle.exec.CheckstyleResults;

import java.io.IOException;

/**
 * @author Olivier Lamy
 * @since 2.4
 */
@Component(role = CheckstyleRssGenerator.class, hint = "default")
public class DefaultCheckstyleRssGenerator implements CheckstyleRssGenerator {

    @Requirement
    private VelocityComponent velocityComponent;

    /**
     * @see CheckstyleRssGenerator#generateRSS(CheckstyleResults, CheckstyleRssGeneratorRequest)
     */
    public void generateRSS(CheckstyleResults results, CheckstyleRssGeneratorRequest checkstyleRssGeneratorRequest)
            throws MavenReportException {

        VelocityTemplate vtemplate = new VelocityTemplate(velocityComponent, CheckstyleReport.PLUGIN_RESOURCES);
        vtemplate.setLog(checkstyleRssGeneratorRequest.getLog());

        Context context = new VelocityContext();
        context.put("results", results);
        context.put("project", checkstyleRssGeneratorRequest.getMavenProject());
        context.put("copyright", checkstyleRssGeneratorRequest.getCopyright());
        context.put("levelInfo", SeverityLevel.INFO);
        context.put("levelWarning", SeverityLevel.WARNING);
        context.put("levelError", SeverityLevel.ERROR);
        context.put("stringutils", new StringUtils());

        try {
            vtemplate.generate(checkstyleRssGeneratorRequest.getOutputDirectory().getPath() + "/checkstyle.rss",
                    "checkstyle-rss.vm", context);
        } catch (ResourceNotFoundException e) {
            throw new MavenReportException("Unable to find checkstyle-rss.vm resource.", e);
        } catch (MojoExecutionException | IOException | VelocityException e) {
            throw new MavenReportException("Unable to generate checkstyle.rss.", e);
        }
    }

}
