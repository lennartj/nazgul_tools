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
package se.jguru.nazgul.tools.visualization.api;

/**
 * Renderable which renders its content as a String. {@link StringRenderable} objects are pretty-printable, and hence
 * support indentation (levels) intended to prefix the output from a rendering.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public interface StringRenderable extends Renderable<String> {

    /**
     * OS-neutral newline String/char.
     */
    String NEWLINE = System.getProperty("line.separator");

    /**
     * A default indentation string, holding 2 whitespaces.
     */
    String TWO_SPACES = "  ";

    /**
     * Assigns the indentation level of this {@link Renderable}.
     *
     * @param indentationLevel The non-negative level of indentation.
     * @throws IllegalArgumentException if the indentationLevel argument was negative.
     */
    void setIndentationLevel(final int indentationLevel) throws IllegalArgumentException;

    /**
     * Retrieves the indentation level of this {@link Renderable}.
     *
     * @return the indentation level of this {@link Renderable}.
     */
    int getIndentationLevel();

    /**
     * Retrieves an indentation string, which is echoed once for each indentation level to create
     * a pretty-printed rendering.
     *
     * @return the default single indentation string.
     */
    String getSingleIndent();
}
