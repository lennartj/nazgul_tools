/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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

package se.jguru.nazgul.tools.visualization.model.jaxb;

import org.apache.commons.lang3.Validate;
import org.junit.rules.TestWatcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>jUnit rule simplifying working with JAXB marshalling and unmarshalling during tests.
 * Typically, this rule is invoked in 3 steps for marshalling and 2 steps for unmarshalling.</p>
 * <h2>Marshalling Objects to XML Strings</h2>
 * <ol>
 * <li>Call <code>add(class1, class2, ...);</code> to add any classes that should be bound into the
 * JAXBContext for marshalling or unmarshalling.</li>
 * <li>(Optional): Call <code>mapXmlNamespacePrefix(anXmlURI, marshalledXmlPrefix)</code>
 * to control the XML namespace prefix in the marshalled structure.</li>
 * <li>Call <code>marshalToXML(classLoader, anObject)</code> to marshalToXML the objects into XML</li>
 * </ol>
 * <h2>Unmarshalling Objects from XML Strings</h2>
 * <ol>
 * <li>Call <code>add(class1, class2, ...);</code> to add any classes that should be bound into the
 * JAXBContext for marshalling or unmarshalling.</li>
 * <li>(Optional): Call <code>mapXmlNamespacePrefix(anXmlURI, marshalledXmlPrefix)</code>
 * to control the XML namespace prefix in the marshalled structure.</li>
 * <li>Call <code>unmarshal(classLoader, ResultClass.class, xmlString);</code> to unmarshal the XML String into
 * Java Objects.</li>
 * </ol>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class PlainJaxbContextRule extends TestWatcher {

    private static final Comparator<Class<?>> CLASS_COMPARATOR = (class1, class2) -> {

        // Deal with nulls.
        final String className1 = class1 == null ? "" : class1.getName();
        final String className2 = class2 == null ? "" : class2.getName();

        // All done
        return className1.compareTo(className2);
    };

    /**
     * The JAXBContextFactory implementation within EclipseLink (i.e. the MOXy implementation).
     */
    public static final String ECLIPSELINK_JAXB_CONTEXT_FACTORY = "org.eclipse.persistence.jaxb.JAXBContextFactory";
    private static final String JAXB_CONTEXTFACTORY_PROPERTY = "javax.xml.bind.context.factory";
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String ECLIPSELINK_MEDIA_TYPE = "eclipselink.media-type";
    private static final String ECLIPSELINK_JSON_MARSHAL_EMPTY_COLLECTIONS
            = "eclipselink.json.marshal-empty-collections";
    private static final String ECLIPSELINK_JSON_WRAPPER_AS_ARRAY_NAME
            = "eclipselink.json.wrapper-as-array-name";
    private static final SortedSet<String> STD_IGNORED_CLASSPATTERNS;

    static {
        STD_IGNORED_CLASSPATTERNS = new TreeSet<>();
        STD_IGNORED_CLASSPATTERNS.add("org.aspectj");
        STD_IGNORED_CLASSPATTERNS.add("ch.");
        STD_IGNORED_CLASSPATTERNS.add("org.slf4j");
    }

    // Internal state
    private SortedSet<Class<?>> jaxbAnnotatedClasses;
    private SortedSet<String> classPatternsToIgnore;
    private JAXBContext jaxbContext;
    private boolean useEclipseLinkMOXyIfAvailable = true;
    private SortedMap<String, Object> marshallerProperties;
    private SortedMap<String, Object> unMarshallerProperties;

    private Predicate<Class<?>> ignoredClassFilter = aClass -> {

        // Don't accept nulls.
        if (aClass == null) {
            return false;
        }

        // Don't accept classes whose names contain any of the classPatternsToIgnore.
        final String className = aClass.getName();
        for (String current : classPatternsToIgnore) {
            if (className.contains(current)) {
                return false;
            }
        }

        // Accept the aClass.
        return true;
    };

    /**
     * Default constructor, setting up a clean internal state.
     */
    public PlainJaxbContextRule() {

        this.jaxbAnnotatedClasses = new TreeSet<>(CLASS_COMPARATOR);
        this.classPatternsToIgnore = new TreeSet<>();
        this.classPatternsToIgnore.addAll(STD_IGNORED_CLASSPATTERNS);

        // Assign standard properties for the Marshaller
        marshallerProperties = new TreeMap<>();
        marshallerProperties.put(Marshaller.JAXB_ENCODING, "UTF-8");
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Assign standard properties for the Unmarshaller
        unMarshallerProperties = new TreeMap<>();
    }

    /**
     * If {@code false}, the JAXB reference implementation will be used for JAXB operations,
     * and otherwise the MOXy implementation from EclipseLink.
     *
     * @param useEclipseLinkMOXyIfAvailable if {@code false}, the JAXB reference implementation is used.
     * @see #useEclipseLinkMOXyIfAvailable
     */
    public void setUseEclipseLinkMOXyIfAvailable(final boolean useEclipseLinkMOXyIfAvailable) {

        // Check sanity
        this.useEclipseLinkMOXyIfAvailable = useEclipseLinkMOXyIfAvailable;
    }

    /**
     * <p>Adds a set of classes to be used within the JAXBContext for marshalling or unmarshalling.
     * Normally, these classes would need to be annotated with JAXB annotations, and would be injected into the
     * construction of the JAXBContext normally:</p>
     * <pre>
     *     <code>
     *         // Create an array of all added classes.
     *         final Class[] classesToBeBound = ... all add-ed Classes ...
     *
     *         // Create the JAXBContext containing/binding all added classes.
     *         JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
     *     </code>
     * </pre>
     *
     * @param jaxbAnnotatedClasses The classes to add to any JAXBContext used within this PlainJaxbContextRule, for
     *                             marshalling or unmarshalling. The supplied classes are given to the JAXBContext
     *                             during creation.
     */
    public void add(final Class<?>... jaxbAnnotatedClasses) {
        if (jaxbAnnotatedClasses != null) {
            Collections.addAll(this.jaxbAnnotatedClasses, jaxbAnnotatedClasses);
        }
    }

    /**
     * Adds patterns for classes to ignore in creating a JAXBContext.
     *
     * @param clearExistingPatterns if {@code true}, any existing patterns are cleared before adding the supplied
     *                              ignore patterns. Typically something like {@code org.aspectj}.
     * @param ignorePattern         A set of patterns to ignore if present within JAXContext classes.
     */
    public void addIgnoreClassPatterns(final boolean clearExistingPatterns, final String... ignorePattern) {

        // Handle clearing existing patterns
        if (clearExistingPatterns) {
            classPatternsToIgnore.clear();
        }

        // ... and add the supplied ones.
        if (ignorePattern != null && ignorePattern.length > 0) {
            Collections.addAll(classPatternsToIgnore, ignorePattern);
        }
    }

    /**
     * Retrieves the set of properties used within the Marshaller.
     *
     * @return the properties assigned to the Marshaller before use.
     */
    public SortedMap<String, Object> getMarshallerProperties() {
        return marshallerProperties;
    }

    /**
     * Retrieves the set of properties used within the Unmarshaller.
     *
     * @return the properties assigned to the Unmarshaller before use.
     */
    public SortedMap<String, Object> getUnMarshallerProperties() {
        return unMarshallerProperties;
    }

    /**
     * Adds the supplied patterns for classes to ignore in creating a JAXBContext, without clearing any existing
     * patterns.
     *
     * @param ignorePattern A set of patterns to ignore if present within JAXContext classes.
     * @see #addIgnoreClassPatterns(boolean, String...)
     */
    public void addIgnoreClassPatterns(final String... ignorePattern) {
        addIgnoreClassPatterns(false, ignorePattern);
    }

    /**
     * Marshals the supplied objects into an XML String, or throws an IllegalArgumentException
     * containing a wrapped JAXBException indicating why the marshalling was unsuccessful.
     *
     * @param loader   The ClassLoader to use in order to load all classes previously added
     *                 by calls to the {@code add} method.
     * @param emitJSON if {@code true}, the method will attempt to output JSON instead of XML.
     *                 This requires the EclipseLink MOXy implementation as the JAXBContextFactory.
     * @param schema   An optional (i.e. nullable) Schema, used to validate output from the Marshaller.
     * @param objects  The objects to Marshal into XML.
     * @return An XML-formatted String containing
     * @throws IllegalArgumentException if the marshalling operation failed.
     *                                  The {@code cause} field in the IllegalArgumentException contains
     *                                  the JAXBException thrown by the JAXB framework.
     * @see #add(Class[])
     */
    @SuppressWarnings("all")
    public String marshal(final ClassLoader loader,
                          final boolean emitJSON,
                          final Schema schema,
                          final Object... objects)
            throws IllegalArgumentException {

        // Use EclipseLink?
        if (emitJSON) {
            setUseEclipseLinkMOXyIfAvailable(true);
        }
        if (useEclipseLinkMOXyIfAvailable) {
            System.setProperty(JAXB_CONTEXTFACTORY_PROPERTY, ECLIPSELINK_JAXB_CONTEXT_FACTORY);
        } else {
            System.clearProperty(JAXB_CONTEXTFACTORY_PROPERTY);
        }

        // Create the JAXBContext
        try {
            jaxbContext = JAXBContext.newInstance(getClasses(loader, null));
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Could not create JAXB context.", e);
        }

        // Create the JAXB Marshaller
        Marshaller marshaller = null;
        try {

            marshaller = jaxbContext.createMarshaller();

            // Should we validate the output using the supplied Schema?
            if (schema != null) {
                marshaller.setSchema(schema);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Could not create JAXB Marshaller", e);
        }

        // Should we emit JSON instead of XML?
        if (emitJSON) {
            try {
                marshaller.setProperty(ECLIPSELINK_MEDIA_TYPE, JSON_CONTENT_TYPE);
                marshaller.setProperty(ECLIPSELINK_JSON_WRAPPER_AS_ARRAY_NAME, Boolean.TRUE);
                marshaller.setProperty(ECLIPSELINK_JSON_MARSHAL_EMPTY_COLLECTIONS, Boolean.FALSE);
            } catch (PropertyException e) {
                // This is likely not the EclipseLink Marshaller.
            }
        }

        // Assign all other Marshaller properties.
        try {
            for (Map.Entry<String, Object> current : marshallerProperties.entrySet()) {
                marshaller.setProperty(current.getKey(), current.getValue());
            }
        } catch (PropertyException e) {
            final StringBuilder builder = new StringBuilder("Could not assign Marshaller properties.");
            marshallerProperties.entrySet().stream().forEach(c -> builder.append("\n  ["
                    + c.getKey() + "]: " + c.getValue()));

            throw new IllegalStateException(builder.toString(), e);
        }

        // Marshal the objects
        final StringWriter result = new StringWriter();
        for (int i = 0; i < objects.length; i++) {
            final StringWriter tmp = new StringWriter();
            try {
                marshaller.marshal(objects[i], tmp);
                result.write(tmp.toString());
            } catch (JAXBException e) {
                final String currentTypeName = objects[i] == null ? "<null>" : objects[i].getClass().getName();
                throw new IllegalArgumentException("Could not marshalToXML object [" + i + "] of type ["
                        + currentTypeName + "].", e);
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not marshalToXML object [" + i + "]: " + objects[i], e);
            }
        }

        // All done.
        return result.toString();
    }

    /**
     * <p>Unmarshals the supplied xmlToUnmarshal into a result of the supplied type, using the given
     * ClassLoader to load all relevant types into the JAXBContext. Typical unarshalling use case involves
     * 2 calls on this PlainJaxbContextRule:</p>
     * <pre>
     *     <code>
     *         // 1) add all types you expect to unmarshal
     *         rule.add(Foo.class, Bar.class, Gnat.class);
     *
     *         // 2) unmarshal your XML string
     *         Foo unmarshalled = rule.unmarshal(getClass().getClassLoader(), Foo.class, aFooXml);
     *     </code>
     * </pre>
     *
     * @param loader          The ClassLoader to use in order to load all classes previously added
     *                        by calls to the {@code add} method.
     * @param assumeJSonInput If {@code true}, the input is assumed to be JSON.
     *                        This requires the EclipseLink MOXy JAXBContextFactory to succeed.
     * @param resultType      The type of the resulting object.
     * @param toUnmarshal     The XML string to unmarshal into a T object.
     * @param <T>             The expected type to unmarshal into.
     * @return The resulting, unmarshalled object.
     * @see #add(Class[])
     */
    public <T> T unmarshal(final ClassLoader loader,
                           final boolean assumeJSonInput,
                           final Class<T> resultType,
                           final String toUnmarshal) {

        // Check sanity
        Validate.notNull(resultType, "resultType");
        Validate.notEmpty(toUnmarshal, "xmlToUnmarshal");

        final Source source = new StreamSource(new StringReader(toUnmarshal));

        // Use EclipseLink?
        if (assumeJSonInput || useEclipseLinkMOXyIfAvailable) {
            System.setProperty(JAXB_CONTEXTFACTORY_PROPERTY, ECLIPSELINK_JAXB_CONTEXT_FACTORY);
        } else {
            System.clearProperty(JAXB_CONTEXTFACTORY_PROPERTY);
        }

        try {
            jaxbContext = JAXBContext.newInstance(getClasses(loader, null));
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Could not create JAXB context.", e);
        }

        try {
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Assign all unMarshallerProperties to the Unmarshaller
            unMarshallerProperties.entrySet().stream().forEach(c -> {
                try {
                    unmarshaller.setProperty(c.getKey(), c.getValue());
                } catch (PropertyException e) {
                    throw new IllegalStateException("Could not assign Unmarshaller property [" + c.getKey()
                            + "] with value [" + c.getValue() + "]", e);
                }
            });

            if (assumeJSonInput) {
                try {
                    unmarshaller.setProperty(ECLIPSELINK_MEDIA_TYPE, JSON_CONTENT_TYPE);
                    unmarshaller.setProperty(ECLIPSELINK_JSON_WRAPPER_AS_ARRAY_NAME, Boolean.TRUE);
                } catch (PropertyException e) {
                    // This is likely not the EclipseLink Marshaller.
                }
            }

            // All Done.
            return unmarshaller.unmarshal(source, resultType).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Could not unmarshal xml into [" + resultType.getName() + "]", e);
        }
    }

    /**
     * Unmarshals without type information resulting in an Object, when no resulting type information
     * has been (or can be) given.
     *
     * @param loader         The ClassLoader to use in order to load all classes previously added
     *                       by calls to the {@code add} method.
     * @param xmlToUnmarshal The XML string to unmarshal into a T object.
     * @return The resulting, unmarshalled object.
     * @see #unmarshal(ClassLoader, boolean, Class, String)
     */
    public Object unmarshal(final ClassLoader loader, final boolean assumeJSonInput, final String xmlToUnmarshal) {
        return unmarshal(loader, assumeJSonInput, Object.class, xmlToUnmarshal);
    }

    /**
     * Helper utility class to create a StreamSource from a supplied resource path.
     *
     * @param resourcePath A non-null resource path.
     * @return The {@link StreamSource} corresponding to the given resourcePath.
     */
    public static StreamSource getSource(final String resourcePath) {

        if (resourcePath == null) {
            throw new IllegalArgumentException("Cannot handle null 'resourcePath' argument.");
        }

        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final String effectiveResourcePath = resourcePath.charAt(0) == '/'
                ? resourcePath.substring(1)
                : resourcePath;
        final InputStream data = contextClassLoader.getResourceAsStream(effectiveResourcePath);

        // All Done.
        return new StreamSource(data);
    }

    /**
     * Convenience method to read the content of a resource fully.
     *
     * @param resourcePath The resource path.
     * @return The resource, converted to a String.
     */
    public static String readFully(final String resourcePath) {

        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final String effectiveResourcePath = resourcePath.charAt(0) == '/'
                ? resourcePath.substring(1)
                : resourcePath;

        final BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        contextClassLoader.getResourceAsStream(effectiveResourcePath)));
        final StringWriter out = new StringWriter();

        // Perform a Buffered read.
        final char[] buffer = new char[1024 * 4];
        int n = 0;
        try {
            while (-1 != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read resource [" + resourcePath + "]", e);
        }

        // All Done.
        return out.toString();
    }

    //
    // Private helpers
    //

    private <C extends Collection<String>> Class[] getClasses(final ClassLoader loader, final C input) {

        final ClassLoader effectiveClassLoader = loader == null ? PlainJaxbContextRule.class.getClassLoader() : loader;
        final SortedMap<String, Class<?>> name2ClassMap = new TreeMap<>();

        if (input != null) {
            for (String current : input) {
                try {
                    final Class<?> aClass = effectiveClassLoader.loadClass(current);
                    if (aClass != null) {
                        name2ClassMap.put(aClass.getName(), aClass);
                    }
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException("Could not load class for [" + current + "]", e);
                }
            }
        }

        // Add any explicitly added classes to the JAXBContext
        for (Class<?> current : this.jaxbAnnotatedClasses) {
            name2ClassMap.put(current.getName(), current);
        }

        // Remove any ignored classes.
        final List<Class<?>> classList = name2ClassMap
                .values()
                .stream()
                .filter(ignoredClassFilter)
                .collect(Collectors.toList());

        // All done.
        return classList.toArray(new Class<?>[classList.size()]);
    }
}

