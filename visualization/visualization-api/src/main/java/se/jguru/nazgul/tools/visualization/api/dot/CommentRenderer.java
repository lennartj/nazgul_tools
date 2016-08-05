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
        final StringBuilder builder = new StringBuilder(config.getIndent());
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
        return builder.toString();
    }
}
