package se.jguru.nazgul.tools.plugin.checkstyle.report.component

import se.jguru.nazgul.tools.plugin.checkstyle.CheckstyleResults
import se.jguru.nazgul.tools.plugin.checkstyle.integration.CheckstyleParameters
import java.io.Serializable

/**
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
interface ReportGenerator<out T : Serializable> {

    fun generateReport(params : CheckstyleParameters, results : CheckstyleResults)
}