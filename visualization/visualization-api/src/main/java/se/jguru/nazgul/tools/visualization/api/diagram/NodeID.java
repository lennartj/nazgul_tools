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
package se.jguru.nazgul.tools.visualization.api.diagram;

import se.jguru.nazgul.tools.visualization.api.StringRenderable;

/**
 * NodeID statement Renderer, complying to the specification in the
 * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class NodeID extends AbstractStringIdentifiable {

    /**
     * An enumeration corresponding to
     * <strong><code>compass_pt : (n | ne | e | se | s | sw | w | nw | c | _)</code></strong> in the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     */
    public enum CompassPoint implements StringRenderable {
        NORTH("n"),
        NORTH_EAST("ne"),
        EAST("e"),
        SOUTH_EAST("se"),
        SOUTH("s"),
        SOUTH_WEST("sw"),
        WEST("w"),
        NORTH_WEST("nw"),
        CENTER("c"),
        UNKNOWN("_");

        // Internal state
        private String dotToken;

        CompassPoint(final String dotToken) {
            this.dotToken = dotToken;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String render() {
            return dotToken;
        }
    }

    /**
     * A Port which corresponds to the following
     * <strong><code>port : ':' ID [ ':' compass_pt ] | ':' compass_pt</code></strong> in the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     */
    public static class Port implements StringRenderable {

        // Internal state
        private String identifier;
        private CompassPoint compassPoint;

        /**
         * Convenience constructor for an anonymous Port with the supplied {@link CompassPoint}.
         *
         * @param compassPoint A non-null {@link CompassPoint} value.
         */
        public Port(final CompassPoint compassPoint) {
            this(null, compassPoint);
        }

        /**
         * Compound constructor, creating a Port wrapping the supplied data.
         *
         * @param identifier   An optional (i.e. nullable) identifier for this Port.
         * @param compassPoint A non-null {@link CompassPoint} value.
         */
        public Port(final String identifier, final CompassPoint compassPoint) {

            // Check sanity
            if (compassPoint == null) {
                throw new NullPointerException("Cannot hanlde null 'compassPoint' argument.");
            }

            // Assign internal state
            this.identifier = identifier;
            this.compassPoint = compassPoint;
        }

        /**
         * Retrieves the optional (i.e. nullable) identifier of this NodeID.
         *
         * @return the optional (i.e. nullable) identifier of this NodeID.
         */
        public String getId() {
            return identifier;
        }

        /**
         * The non-null CompassPoint of this NodeID.
         *
         * @return the non-null CompassPoint of this NodeID.
         */
        public CompassPoint getCompassPoint() {
            return compassPoint;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String render() {

            final String prefix = identifier != null && !identifier.isEmpty()
                    ? ": " + identifier + " "
                    : "";

            // All Done.
            return prefix + ": " + compassPoint.render();
        }
    }

    // Internal state
    private Port port;

    /**
     * Compound constructor creating a {@link NodeID} with the supplied ID and optional
     * (i.e. nullable) Port values. Corresponds to <strong><code>node_id : ID [ port ]</code></strong> in the
     * <a href="http://www.graphviz.org/content/dot-language">DOT language specification</a>.
     *
     * @param id   a non-null and non-empty identifier, assumed to be unique within a Graph.
     * @param port An optional (i.e. nullable) Port for this {@link NodeID}.
     */
    public NodeID(final String id, final Port port) {
        super(id);
        this.port = port;
    }

    /**
     * Assigns a Port to this {@link NodeID}.
     *
     * @param port a non-null Port instance.
     */
    public void setPort(final Port port) {

        // Check sanity
        if (port == null) {
            throw new NullPointerException("Cannot handle null 'port' argument.");
        }

        // Assign internal state
        this.port = port;
    }

    /**
     * @return The optional (i.e. nullable) Port of this NodeID.
     */
    public Port getPort() {
        return port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render() {
        return getId() + (port != null ? " " + port.render() : "") + " ";
    }
}
