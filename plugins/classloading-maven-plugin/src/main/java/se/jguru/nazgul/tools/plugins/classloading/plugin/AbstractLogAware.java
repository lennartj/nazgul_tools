/*
 * #%L
 * Nazgul Project: nazgul-tools-classloading-maven-plugin
 * %%
 * Copyright (C) 2010 - 2014 jGuru Europe AB
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
package se.jguru.nazgul.tools.plugins.classloading.plugin;

import org.apache.commons.lang.Validate;
import org.apache.maven.plugin.logging.Log;

/**
 * Common ancestor for classes requiring a Maven Log.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public abstract class AbstractLogAware {

    /**
     * The Maven Log used by this AbstractLogAware. Never {@code null}.
     */
    protected final Log log;

    /**
     * Creates a new AbstractLogAware wrapping the supplied log.
     *
     * @param log A non-null Maven Log used by this AbstractLogAware.
     */
    protected AbstractLogAware(final Log log) {

        // Check sanity
        Validate.notNull(log, "Cannot handle null log argument.");

        // Assign internal state
        this.log = log;
    }

    /**
     * Checks if the given object was {@code null}, and - if so - emits a warning in the supplied Maven Log.
     *
     * @param object       The object to check for null.
     * @param argumentName The name of the argument which is checked for null.
     *                     This argumentName should not be null itself.
     * @return {@code true} if the given object was null.
     */
    protected boolean isNull(final Object object, final String argumentName) {

        if (object == null) {
            log.warn("Received null [" + argumentName + "] argument.");
            return true;
        }

        // The object was not null
        return false;
    }
}
