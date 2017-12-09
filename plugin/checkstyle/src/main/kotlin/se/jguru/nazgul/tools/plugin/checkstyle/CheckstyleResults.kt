package se.jguru.nazgul.tools.plugin.checkstyle

import com.puppycrawl.tools.checkstyle.api.AuditEvent
import com.puppycrawl.tools.checkstyle.api.Configuration
import com.puppycrawl.tools.checkstyle.api.SeverityLevel
import java.io.Serializable

/**
 * Result holder for a Checkstyle run.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
data class CheckstyleResults(val configuration: Configuration,
                             val files: MutableMap<String, MutableList<AuditEvent>> = mutableMapOf()) : Serializable {

    fun fileCount() : Int = files.size

    fun getFileViolations(fileName: String): MutableList<AuditEvent> = files.getOrPut(fileName, { mutableListOf() })


    fun getSeverityCount(level: SeverityLevel): Long {

        return files.values
                .asSequence()
                .map { getSeverityCountFor(it, level) }
                .sum()
    }

    fun getSeverityCountFor(events : List<AuditEvent>, severityLevel: SeverityLevel) : Long {

        var count: Long = 0

        events.stream()
                .filter { it.severityLevel == severityLevel }
                .forEach { count++ }

        return count
    }
}