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

package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.Comment;

import java.util.List;

/**
 * AbstractStringRenderer implementation for rendering Comments,
 * using either C-style single-line '//' or multiple-line delimiters,
 * depending on how many lines were added to the Comments being rendered.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommentRenderer extends AbstractStringRenderer<Comment> {

    /**
     * Default constructor.
     */
    public CommentRenderer() {
        super(Comment.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final Comment comment) {

        // Check sanity
        final StringBuilder builder = new StringBuilder(config.getNewline() + config.getIndent());
        final List<String> commentLines = comment.getCommentLines();

        if (commentLines.size() == 1) {

            // Use a '//' single-line comment
            builder.append("// ").append(commentLines.get(0));

        } else if (commentLines.size() > 1) {

            // Use a '/*' ... '*/' multi-line comment
            builder.append("/* ").append(config.getNewline());
            for (String current : commentLines) {
                builder.append(config.getIndent()).append(" * ").append(current).append(config.getNewline());
            }
            builder.append(config.getIndent()).append(" */");
        }

        // All Done.
        return builder.toString() + config.getNewline();
    }
}
