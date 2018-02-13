/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-spi-doclet
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
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

package se.jguru.nazgul.tools.visualization.spi.doclet;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

/**
 * <h2>What is a <strong>delegating</strong> Doclet?</h2>
 * <p>Doclet implementation which delegates its actual implementation to a {@link SimpleDoclet} implementation.
 * The Doclet specification within the JDK requires a Doclet implementation to have only static methods, implying
 * that standard inheritance is not normally available for Doclet development. The simpler option is to delegate
 * doclet execution to another class - and this is what the DelegatingDoclet does.</p>
 *
 * <h3>Finding the SimpleDoclet class</h3>
 * <p>When loading the DelegatingDoclet class, it looks for a {@link SimpleDoclet} implementation to which it will
 * delegate all doclet-related work. The delegation is done in 3 steps:</p>
 * <ol>
 *     <li><strong>Environment property</strong>. If {@code System.getenv(ENV_SIMPLEDOCLET_CLASS)} returns a
 *     non-null value, the DelegatingDoclet assumes the result is the fully qualified classname of a SimpleDoclet
 *     implementation which is loaded and used as delegate. The class must contain a no-argument constructor.</li>
 *     <li><strong>System property</strong>. If {@code System.getProperty(PROPERTY_SIMPLEDOCLET_CLASS)} returns a
 *     non-null value, the DelegatingDoclet assumes the result is the fully qualified classname of a SimpleDoclet
 *     implementation which is loaded and used as delegate. The class must contain a no-argument constructor.</li>
 *     <li><strong>Default</strong>. If Doclet classname is found, the DelegatingDoclet uses
 *     {@link VisualizationDoclet} as the SimpleDoclet to which doclet-related work should be delegated.</li>
 * </ol>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class DelegatingDoclet {

    // Internal state
    private static SimpleDoclet delegate;

    /**
     * Environment property used to read a fully qualified class name of a SimpleDoclet implementation which should
     * sport a no-argument constructor.
     */
    public static final String ENV_SIMPLEDOCLET_CLASS = "SIMPLEDOCLET_CLASS";

    /**
     * Java System property used to read a fully qualified class name of a SimpleDoclet implementation which should
     * sport a no-argument constructor. This is used only if the environment property {@link #ENV_SIMPLEDOCLET_CLASS}
     * did not hold any value.
     */
    public static final String PROPERTY_SIMPLEDOCLET_CLASS = "simpledoclet.class";

    static {

        String simpleDocletClassName = System.getenv(ENV_SIMPLEDOCLET_CLASS);
        if (simpleDocletClassName == null) {
            simpleDocletClassName = System.getProperty(
                    PROPERTY_SIMPLEDOCLET_CLASS, VisualizationDoclet.class.getName());
        }

        // Assign the SimpleDoclet delegate.
        setDelegate(createSimpleDoclet(simpleDocletClassName));
    }

    /**
     * Creates a SimpleDoclet instance by calling a no-argument constructor within the Class of the supplied ClassName.
     * Falls back to {@link VisualizationDoclet}.
     *
     * @param className A fully qualified classname of a SimpleDoclet implementation.
     * @return An instance of the supplied ClassName - or an {@link VisualizationDoclet} as a fallback.
     */
    public static SimpleDoclet createSimpleDoclet(final String className) {

        final String effectiveClassName = className != null && !className.isEmpty()
                ? className
                : VisualizationDoclet.class.getName();

        final ClassLoader[] classLoaders = new ClassLoader[]{
                Thread.currentThread().getContextClassLoader(),
                DelegatingDoclet.class.getClassLoader()};

        SimpleDoclet toReturn = null;

        for (ClassLoader current : classLoaders) {
            try {
                final Class<?> loadedClass = current.loadClass(effectiveClassName);
                if (SimpleDoclet.class.isAssignableFrom(loadedClass)) {
                    toReturn = (SimpleDoclet) loadedClass.newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // All Done.
        return toReturn == null ? new VisualizationDoclet() : toReturn;
    }

    /**
     * Assigns the {@link SimpleDoclet} delegate used within this {@link DelegatingDoclet}.
     *
     * @param delegate A non-null {@link SimpleDoclet} implementation
     */
    public static void setDelegate(final SimpleDoclet delegate) {

        if (delegate == null) {
            throw new NullPointerException("Cannot handle null 'delegate' argument.");
        }

        // Assign internal state
        DelegatingDoclet.delegate = delegate;
    }

    /**
     * Doclet start of execution.
     *
     * @param root A non-null {@link RootDoc} that represents the root of the program structure information
     *             for one run of javadoc. Enables extracting all other program structure information data.
     * @return true if the start completed OK.
     */
    public static boolean start(final RootDoc root) {
        return delegate.start(root);
    }

    /**
     * <p>Checks that the supplied options are valid for this Doclet, and uses the supplied {@link DocErrorReporter}
     * instance to report any errors. According to the doclet specification:</p>
     * <blockquote>If the validOptions method is present, it is automatically invoked; you don't have to
     * explicitly call it.
     * It should return {@code true} if the option usage is valid, and false otherwise. You can also print
     * appropriate error messages from validOptions when improper usages of command-line options are found.</blockquote>
     *
     * @param options       The options to validate.
     * @param errorReporter The error reporter used to output error messages, in case the options were not valid.
     * @return {@code true} if the options were valid.
     */
    public static boolean validOptions(final String[][] options, final DocErrorReporter errorReporter) {
        return delegate.validateJavaDocOptions(options, errorReporter);
    }

    /**
     * <p>Any doclet that uses custom options must have a method called optionLength(String option) that returns an
     * int. For each custom option that you want your doclet to recognize, optionLength must return the number of
     * separate pieces or tokens in the option.</p>
     * <p>For example, the custom option {@code -foo bar} has 2 pieces, the -tag option itself and its value.
     * Hence, this optionLength method must return 2 for the {@code -foo} option.</p>
     *
     * @param option A javadoc option.
     * @return The length of the supplied option, including the option itself, or {@code 0} for unrecognized options.
     */
    public static int optionLength(final String option) {
        return delegate.optionLength(option);
    }

    /**
     * The JavaDoc language version for this Doclet.
     *
     * @return the JavaDoc language version for this Doclet.
     * @see LanguageVersion#JAVA_1_5
     */
    public static LanguageVersion languageVersion() {
        return delegate.languageVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DelegatingDoclet using [" + (delegate == null ? "<no>" : delegate.getClass().getName())
                + "] SimpleDoclet delegate.";
    }
}
