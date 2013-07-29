/*
 * #%L
 * Nazgul Project: nazgul-tools-codestyle
 * %%
 * Copyright (C) 2010 - 2013 jGuru Europe AB
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

import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MockEnforcerRuleHelper implements EnforcerRuleHelper {

    private final MavenProject project;

    public MockEnforcerRuleHelper(final MavenProject project) {
        this.project = project;
    }

    @Override
    public File alignToBaseDirectory(final File file) {
        return null;
    }

    @Override
    public Object evaluate(final String expression) throws ExpressionEvaluationException {
        if ("${project}".equals(expression)) {
            return project;
        }
        return null;
    }

	@Override
	@SuppressWarnings("rawtypes")
    public Object getComponent(final Class clazz) throws ComponentLookupException {
        return null;
    }

    @Override
    public Object getComponent(final String componentKey) throws ComponentLookupException {
        return null;
    }

    @Override
    public Object getComponent(final String role, final String roleHint) throws ComponentLookupException {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List getComponentList(final String role) throws ComponentLookupException {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Map getComponentMap(final String role) throws ComponentLookupException {
        return null;
    }

    @Override
    public PlexusContainer getContainer() {
        return null;
    }

    @Override
    public Log getLog() {
        return new SystemStreamLog();
    }
}
