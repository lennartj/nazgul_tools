/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
 * %%
 * Copyright (C) 2010 - 2016 jGuru Europe AB
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
package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Abstract implementation of a transport class having a Class for (Map) key.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"className"})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractClassKeyedTransport<T> implements Serializable {

    // Internal state
    @XmlAttribute(required = true)
    private String className;

    /**
     * Default constructor for framework use only.
     */
    public AbstractClassKeyedTransport() {
        // Do nothing
    }

    /**
     * Compound constructor creating an {@link AbstractClassKeyedTransport} instance wrapping the supplied Class.
     *
     * @param aClass A non-null Class object.
     */
    public AbstractClassKeyedTransport(final Class<T> aClass) {

        // Check sanity
        if (aClass == null) {
            throw new IllegalArgumentException("Cannot handle null 'aClass' argument.");
        }

        // Assign internal state
        this.className = aClass.getName();
    }

    /**
     * Retrieves the fully qualified Class name of the wrapped Class.
     *
     * @return the fully qualified Class name of the wrapped Class.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Loads the Class from the supplied Classloader using the {@link #className}.
     *
     * @param aClassLoader A non-null ClassLoader used to load the Class from its {@link #className}.
     * @return The loaded Class.
     */
    public Class<T> getResolvedClass(final ClassLoader aClassLoader) {

        // Check sanity
        if (aClassLoader == null) {
            throw new IllegalArgumentException("Cannot handle null 'aClassLoader' argument.");
        }

        // Load the class and return.
        try {
            return (Class<T>) aClassLoader.loadClass(className);
        } catch (Exception e) {
            throw new IllegalStateException("Could not load/resolve class [" + className + "]", e);
        }
    }

    /**
     * Retrieves the resolved/loaded Class using the Thread contextClassLoader.
     *
     * @return the resolved Class.
     * @see Thread#getContextClassLoader()
     */
    public Class<T> getResolvedClass() {
        return getResolvedClass(Thread.currentThread().getContextClassLoader());
    }
}
