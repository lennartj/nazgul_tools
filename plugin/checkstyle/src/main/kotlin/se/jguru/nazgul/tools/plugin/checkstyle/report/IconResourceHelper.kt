package se.jguru.nazgul.tools.plugin.checkstyle.report

import org.apache.maven.doxia.sink.Sink
import java.util.*

/**
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class IconResourceHelper(private val sink: Sink,
                         private val bundle: ResourceBundle) {

    /**
     * Render a simple icon of given level.
     *
     * @param level one of `INFO`, `WARNING` or `ERROR` constants
     */
    fun iconSeverity(level: String) {
        sink.figure()
        sink.figureGraphics("images/icon_" + level + "_sml.gif")
        sink.figure_()
    }

    /**
     * Render an icon of given level with associated text.
     *
     * @param level    one of `INFO`, `WARNING` or `ERROR` constants
     * @param textType one of `NO_TEXT`, `TEXT_SIMPLE`, `TEXT_TITLE` or
     * `TEXT_ABBREV` constants
     */
    fun iconSeverity(level: String, textType: Int) {
        sink.figure()
        sink.figureGraphics("images/icon_" + level + "_sml.gif")
        sink.figure_()

        if (textType > 0) {
            sink.nonBreakingSpace()

            sink.text(bundle.getString("report.checkstyle." + level + suffix(textType)))
        }
    }

    /**
     * Render an info icon.
     */
    fun iconInfo() {
        iconSeverity(INFO)
    }

    /**
     * Render an info icon with associated text.
     *
     * @param textType one of `NO_TEXT`, `TEXT_SIMPLE`, `TEXT_TITLE` or
     * `TEXT_ABBREV` constants
     */
    fun iconInfo(textType: Int) {
        iconSeverity(INFO, textType)
    }

    /**
     * Render a warning icon.
     */
    fun iconWarning() {
        iconSeverity(WARNING)
    }

    /**
     * Render a warning icon with associated text.
     *
     * @param textType one of `NO_TEXT`, `TEXT_SIMPLE`, `TEXT_TITLE` or
     * `TEXT_ABBREV` constants
     */
    fun iconWarning(textType: Int) {
        iconSeverity(WARNING, textType)
    }

    /**
     * Render an error icon.
     */
    fun iconError() {
        iconSeverity(ERROR)
    }

    /**
     * Render an error icon with associated text.
     *
     * @param textType one of `NO_TEXT`, `TEXT_SIMPLE`, `TEXT_TITLE` or
     * `TEXT_ABBREV` constants
     */
    fun iconError(textType: Int) {
        iconSeverity(ERROR, textType)
    }

    private fun suffix(textType: Int): String {
        when (textType) {
            TEXT_TITLE -> return "s"
            TEXT_ABBREV -> return "s.abbrev"
            else -> return ""
        }
    }

    companion object {

        val INFO = "info"
        val WARNING = "warning"
        val ERROR = "error"

        val NO_TEXT = 0
        val TEXT_SIMPLE = 1
        val TEXT_TITLE = 2
        val TEXT_ABBREV = 3
    }
}