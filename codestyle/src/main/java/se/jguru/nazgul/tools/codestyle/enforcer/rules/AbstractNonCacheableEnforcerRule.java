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

import org.apache.maven.enforcer.rule.api.EnforcerRule;

/**
 * AbstractEnforcerRule implementation which implements a non-cacheable behaviour.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractNonCacheableEnforcerRule extends AbstractEnforcerRule {

    /**
     * Always returns {@code false}.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public final boolean isResultValid(final EnforcerRule cachedRule) {
        return false;
    }

    /**
     * Always returns {@code null}.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public final String getCacheId() {
        return null;
    }
}
