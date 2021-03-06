/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-api
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

package se.jguru.nazgul.tools.visualization.api;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Configuration holder for properties affecting rendering.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 * @see Renderer
 */
public class RenderConfiguration implements Serializable {

    /**
     * OS-neutral newline String/char.
     */
    public static final String NEWLINE = System.getProperty("line.separator");

    /**
     * A default indentation string, holding 2 whitespaces.
     */
    public static final String TWO_SPACES = "  ";

    // Internal state
    private Map<String, Object> extraConfiguration;
    private int indentationLevel;
    private String indentationToken;
    private boolean isDirectedGraph;

    /**
     * Default constructor creating a RenderConfiguration using indentationLevel 0
     * and two spaces for indentationToken (i.e. {@link #TWO_SPACES}).
     */
    public RenderConfiguration() {
        this(0);
    }

    /**
     * Convenience constructor creating a RenderConfiguration with the supplied indentation level and using two
     * spaces for indentationToken (i.e. {@link #TWO_SPACES}).
     *
     * @param indentationLevel The non-negative indentation level.
     */
    public RenderConfiguration(final int indentationLevel) {
        this(indentationLevel, TWO_SPACES, true);
    }

    /**
     * Compound constructor creating a RenderConfiguration wrapping the supplied data.
     *
     * @param indentationLevel The non-negative indentation level.
     * @param indentationToken The non-empty and non-null indentation token.
     * @param isDirectedGraph  {@code true} to indicate that a directed graph should be rendered.
     */
    public RenderConfiguration(final int indentationLevel,
                               final String indentationToken,
                               final boolean isDirectedGraph) {

        // Assign internal state
        this.extraConfiguration = new TreeMap<>();
        setIndentationLevel(indentationLevel);
        setIndentationToken(indentationToken);
        setDirectedGraph(isDirectedGraph);
    }

    /**
     * @return true if this RenderConfiguration is used within a Directed Graph.
     */
    public boolean isDirectedGraph() {
        return isDirectedGraph;
    }

    /**
     * Assigns true or false to the {@link #isDirectedGraph} property.
     *
     * @param directedGraph true if this RenderConfiguration is used within a Directed Graph.
     */
    public void setDirectedGraph(final boolean directedGraph) {
        isDirectedGraph = directedGraph;
    }

    /**
     * Assigns the active indentationLevel for this RenderConfiguration.
     *
     * @param indentationLevel the active indentationLevel for this RenderConfiguration. Cannot be negative.
     */
    public final void setIndentationLevel(final int indentationLevel) {

        // Check sanity
        if (indentationLevel < 0) {
            throw new IllegalArgumentException("Cannot handle 'indentationLevel' < 0. Got: " + indentationLevel);
        }

        // Assign internal state
        this.indentationLevel = indentationLevel;
    }

    /**
     * Assigns the indentationToken of this RenderConfiguration.
     *
     * @param indentationToken the indentationToken of this RenderConfiguration. Cannot be null or empty.
     */
    public final void setIndentationToken(final String indentationToken) {

        // Check sanity
        if (indentationToken == null || indentationToken.isEmpty()) {
            throw new IllegalArgumentException("Cannot handle null or empty 'indentationToken' argument.");
        }

        // Assign internal state
        this.indentationToken = indentationToken;
    }

    /**
     * Clones this RenderConfiguration, and changes the indentation level (of the returned
     * RenderConfiguration) as indicated.
     *
     * @param indentationChange The change of the indentation level in the returned RenderConfiguration.
     * @return A {@link RenderConfiguration} clone of this one - except that the indentationLevel is changed with the
     * supplied amount.
     * @throws IllegalArgumentException if the indentationLevel after the change is negative.
     * @see #setIndentationLevel(int)
     */
    public final RenderConfiguration cloneAndChangeIndentation(final int indentationChange)
            throws IllegalArgumentException {

        // Check sanity
        final int newIndentationLevel = getIndentationLevel() + indentationChange;
        final RenderConfiguration toReturn = new RenderConfiguration(
                newIndentationLevel,
                indentationToken,
                isDirectedGraph);

        toReturn.getExtraConfiguration().putAll(getExtraConfiguration());

        // All Done.
        return toReturn;
    }

    /**
     * Retrieves a full indent, given the {@link #indentationToken} and {@link #indentationLevel}.
     *
     * @return The full string to be emitted before a properly indented token is rendered.
     */
    public String getIndent() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getIndentationLevel(); i++) {
            builder.append(indentationToken);
        }

        // All Done.
        return builder.toString();
    }

    /**
     * Convenience method to retrieve the {@link #NEWLINE}
     *
     * @return {@link #NEWLINE}
     */
    public String getNewline() {
        return NEWLINE;
    }

    /**
     * Retrieves the Map containing extra configuration for rendering.
     *
     * @return the non-null Map containing extra configuration for rendering.
     */
    public Map<String, Object> getExtraConfiguration() {
        return extraConfiguration;
    }

    /**
     * Retrieves the (non-negative) indentation level of this RenderConfiguration.
     *
     * @return the non-negative indentation level of this RenderConfiguration.
     */
    public int getIndentationLevel() {
        return indentationLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RenderConfiguration: "
                + NEWLINE + " indentationLevel   : " + indentationLevel
                + NEWLINE + " indentationToken   : '" + indentationToken + "'"
                + NEWLINE + " extraConfiguration : " + extraConfiguration;
    }
}
