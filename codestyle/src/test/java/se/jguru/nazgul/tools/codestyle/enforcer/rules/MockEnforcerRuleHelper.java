/*
 * Copyright (c) jGuru Europe AB.
 * All rights reserved.
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

    private MavenProject project;

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
