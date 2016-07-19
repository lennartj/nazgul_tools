package se.jguru.nazgul.tools.visualization.impl.doclet.javadoc;

import com.sun.javadoc.RootDoc;
import com.sun.javadoc.SourcePosition;
import se.jguru.nazgul.tools.visualization.impl.doclet.VisualizationDocletTag;

/**
 * {@link RootDoc} implementation which swallows messages related to the
 * {@link se.jguru.nazgul.tools.visualization.impl.doclet.VisualizationDoclet}.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class VisualizationWrappedRootDoc extends AbstractRootDocWrapper {

    /**
     * Constructs an {@link VisualizationWrappedRootDoc} instance around the supplied {@link RootDoc}.
     *
     * @param wrappedRootDoc A non-null {@link RootDoc} instance to be wrapped by this {@link VisualizationWrappedRootDoc}.
     */
    public VisualizationWrappedRootDoc(final RootDoc wrappedRootDoc) {
        super(wrappedRootDoc);
    }

    /**
     * Delegates to the wrapped {@link RootDoc} only if the supplied Message does not contain an
     * {@link se.jguru.nazgul.tools.visualization.impl.doclet.VisualizationDocletTag} identifier. 
     * Otherwise, the message is ignored.
     *
     * @param msg The warning message.
     */
    @Override
    public void printWarning(final String msg) {

        // Swallow VisualizationDoclet warnings.
        if (!containsVisualizationDocletTag(msg)) {
            wrappedRootDoc.printWarning(msg);
        }
    }

    /**
     * Delegates to the wrapped {@link RootDoc} only if the supplied Message does not contain an
     * VisualizationDocletTag identifier. Otherwise, the message is ignored.
     *
     * @param pos The position in the Source.
     * @param msg The warning message.
     */
    @Override
    public void printWarning(final SourcePosition pos, final String msg) {
        super.printWarning(pos, msg);
    }

    /**
     * Checks if ths supplied message contains an {@link VisualizationDocletTag} reference, on the form
     * {@code '@nazgul_vis.xxxx '}.
     *
     * @param message The message to check.
     * @return {@code true} if the supplied message contains a {@link VisualizationDocletTag} reference.
     */
    public static boolean containsVisualizationDocletTag(final String message) {

        if (message != null) {
            for (VisualizationDocletTag current : VisualizationDocletTag.values()) {
                if (message.contains(current.toString() + " ")) {

                    // The message contains an entry on the form '@nazgul_vis.xxxx '
                    // which implies that it has to do with a VisualizationDoclet javadoc Tag.
                    return true;
                }
            }
        }

        // Nopes.
        return false;
    }
}
