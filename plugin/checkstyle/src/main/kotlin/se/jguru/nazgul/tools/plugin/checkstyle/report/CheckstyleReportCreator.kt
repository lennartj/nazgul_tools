package se.jguru.nazgul.tools.plugin.checkstyle.report

import com.puppycrawl.tools.checkstyle.api.AuditEvent
import com.puppycrawl.tools.checkstyle.api.CheckstyleException
import com.puppycrawl.tools.checkstyle.api.Configuration
import com.puppycrawl.tools.checkstyle.api.SeverityLevel
import org.apache.maven.doxia.sink.Sink
import org.apache.maven.doxia.sink.SinkEventAttributeSet
import org.apache.maven.doxia.sink.SinkEventAttributes
import org.apache.maven.doxia.tools.SiteTool
import org.apache.maven.plugin.logging.Log
import org.codehaus.plexus.util.StringUtils
import se.jguru.nazgul.tools.plugin.checkstyle.IconTool
import se.jguru.nazgul.tools.plugin.checkstyle.RuleUtil
import se.jguru.nazgul.tools.plugin.checkstyle.exec.CheckstyleResults
import java.io.File
import java.util.*

/**
 * Factory class which produces Checkstyle reports.
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class CheckstyleReportCreator(var log: Log,
                              val sink: Sink,
                              val bundle: ResourceBundle,
                              val basedir: File,
                              val siteTool: SiteTool,
                              val ruleset: String,
                              val iconTool: IconTool = IconTool(sink, bundle),
                              var enableSeveritySummary: Boolean = false,
                              var enableRulesSummary: Boolean = false,
                              var enableFilesSummary: Boolean = false,
                              var severityLevel: SeverityLevel? = null,
                              var enableRSS: Boolean = false,
                              var checkstyleConfig: Configuration? = null,
                              var xrefLocation: String? = null,
                              var treeWalkerNames: List<String>? = mutableListOf("TreeWalker")) {


    fun generateReport(results: CheckstyleResults) {

        // #1) Prepare the heading
        emitReportHeading()

        // #2) Prepare the heading
        if (severityLevel == null) {

            if (enableSeveritySummary) {
                doSeveritySummary(results)
            }

            if (enableFilesSummary) {
                doFilesSummary(results)
            }

            if (enableRulesSummary) {
                doRulesSummary(results)
            }
        }

        // #3) Emit the report details
        doDetails(results)

        // #4) Close off the body, flush the stream and be done
        sink.body_()
        sink.flush()
        sink.close()
    }

    //
    // Private helpers
    //

    /**
     * Get the value of the specified attribute from the Checkstyle configuration.
     * If parentConfigurations is non-null and non-empty, the parent
     * configurations are searched if the attribute cannot be found in the
     * current configuration. If the attribute is still not found, the
     * specified default value will be returned.
     *
     * @param config              The current Checkstyle configuration
     * @param parentConfiguration The configuration of the parent of the current configuration
     * @param name                The name of the attribute
     * @param fallbackValue       The default value to use if the attribute cannot be found in any configuration
     * @return The value of the specified attribute
     */
    private fun getConfigAttribute(config: Configuration,
                                   parentConfiguration: ChainedItem<Configuration>?,
                                   name: String,
                                   fallbackValue: String?): String? {


        return try {

            // Found the name
            config.getAttribute(name)

        } catch (e: CheckstyleException) {

            // Try to find the attribute in a parent, if there are any
            if (parentConfiguration != null) {

                getConfigAttribute(parentConfiguration.value,
                        parentConfiguration.parent,
                        name,
                        fallbackValue)
            } else {
                fallbackValue
            }
        }
    }

    private fun getTitle(): String = if (severityLevel == null) {
        bundle.getString("report.checkstyle.title")
    } else {
        bundle.getString("report.checkstyle.severity_title") + severityLevel!!.getName()
    }

    private fun emitReportHeading() {

        sink.head()
        sink.title()
        sink.text(getTitle())
        sink.title_()
        sink.head_()

        sink.body()

        sink.section1()
        sink.sectionTitle1()
        sink.text(getTitle())
        sink.sectionTitle1_()

        sink.paragraph()
        sink.text(bundle.getString("report.checkstyle.checkstylelink") + " ")
        sink.link("http://checkstyle.sourceforge.net/")
        sink.text("Checkstyle")
        sink.link_()
        val version = getCheckstyleVersion()
        if (version != null) {
            sink.text(" ")
            sink.text(version)
        }
        sink.text(" ")
        sink.text(String.format(bundle.getString("report.checkstyle.ruleset"), ruleset))
        sink.text(".")

        if (enableRSS) {
            sink.nonBreakingSpace()
            sink.link("checkstyle.rss")
            sink.figure()
            sink.figureCaption()
            sink.text("rss feed")
            sink.figureCaption_()
            sink.figureGraphics("images/rss.png")
            sink.figure_()
            sink.link_()
        }

        sink.paragraph_()
        sink.section1_()
    }

    /**
     * Create the rules summary section of the report.
     *
     * @param results The results to summarize
     */
    private fun doRulesSummary(results: CheckstyleResults) {
        if (checkstyleConfig == null) {
            return
        }

        sink.section1()
        sink.sectionTitle1()
        sink.text(bundle.getString("report.checkstyle.rules"))
        sink.sectionTitle1_()

        sink.table()

        sink.tableRow()
        sink.tableHeaderCell()
        sink.text(bundle.getString("report.checkstyle.rule.category"))
        sink.tableHeaderCell_()

        sink.tableHeaderCell()
        sink.text(bundle.getString("report.checkstyle.rule"))
        sink.tableHeaderCell_()

        sink.tableHeaderCell()
        sink.text(bundle.getString("report.checkstyle.violations"))
        sink.tableHeaderCell_()

        sink.tableHeaderCell()
        sink.text(bundle.getString("report.checkstyle.column.severity"))
        sink.tableHeaderCell_()

        sink.tableRow_()

        // Top level should be the checker.
        if ("checker".equals(checkstyleConfig!!.name, ignoreCase = true)) {
            var category: String? = null
            for (ref in sortConfiguration(results)) {
                doRuleRow(ref, results, category)

                category = ref.category
            }
        } else {
            sink.tableRow()
            sink.tableCell()
            sink.text(bundle.getString("report.checkstyle.norule"))
            sink.tableCell_()
            sink.tableRow_()
        }

        sink.table_()

        sink.section1_()
    }

    /**
     * Create a summary for one Checkstyle rule.
     *
     * @param ref              The configuration reference for the row
     * @param results          The results to summarize
     * @param previousCategory The previous row's category
     */
    private fun doRuleRow(ref: ConfReference, results: CheckstyleResults, previousCategory: String?) {
        val checkerConfig = ref.configuration
        val parentConfiguration = ref.parentConfiguration
        val ruleName = checkerConfig.name

        sink.tableRow()

        // column 1: rule category
        sink.tableCell()
        val category = ref.category
        if (category != previousCategory) {
            sink.text(category)
        }
        sink.tableCell_()

        // column 2: Rule name + configured attributes
        sink.tableCell()
        if ("extension" != category) {
            sink.link("http://checkstyle.sourceforge.net/config_$category.html#$ruleName")
            sink.text(ruleName)
            sink.link_()
        } else {
            sink.text(ruleName)
        }

        val attribnames = ArrayList(Arrays.asList(*checkerConfig.attributeNames))
        attribnames.remove("severity") // special value (deserves unique column)
        if (!attribnames.isEmpty()) {
            sink.list()
            for (name in attribnames) {
                sink.listItem()

                sink.text(name)

                val value = getConfigAttribute(checkerConfig, null, name, "")
                // special case, Header.header and RegexpHeader.header
                if ("header" == name && ("Header" == ruleName || "RegexpHeader" == ruleName)) {
                    val lines = StringUtils.split(value!!, "\\n")
                    var linenum = 1
                    for (line in lines) {
                        sink.lineBreak()
                        sink.rawText("<span style=\"color: gray\">")
                        sink.text(linenum.toString() + ":")
                        sink.rawText("</span>")
                        sink.nonBreakingSpace()
                        sink.monospaced()
                        sink.text(line)
                        sink.monospaced_()
                        linenum++
                    }
                } else if ("headerFile" == name && "RegexpHeader" == ruleName) {
                    sink.text(": ")
                    sink.monospaced()
                    sink.text("\"")
                    if (basedir != null) {
                        // Make the headerFile value relative to ${basedir}
                        val path = siteTool.getRelativePath(value, basedir.absolutePath)
                        sink.text(path.replace('\\', '/'))
                    } else {
                        sink.text(value)
                    }
                    sink.text("\"")
                    sink.monospaced_()
                } else {
                    sink.text(": ")
                    sink.monospaced()
                    sink.text("\"")
                    sink.text(value)
                    sink.text("\"")
                    sink.monospaced_()
                }
                sink.listItem_()
            }
            sink.list_()
        }

        sink.tableCell_()

        // column 3: rule violation count
        sink.tableCell()
        sink.text(ref.violations.toString())
        sink.tableCell_()

        // column 4: severity
        sink.tableCell()
        // Grab the severity from the rule configuration, this time use error as default value
        // Also pass along all parent configurations, so that we can try to find the severity there
        val severity = getConfigAttribute(checkerConfig, parentConfiguration, "severity", "error")
        iconTool.iconSeverity(severity, IconTool.TEXT_SIMPLE)
        sink.tableCell_()

        sink.tableRow_()
    }

    /**
     * Check if a violation matches a rule.
     *
     * @param event            the violation to check
     * @param ruleName         The name of the rule
     * @param expectedMessage  A message that, if it's not null, will be matched to the message from the violation
     * @param expectedSeverity A severity that, if it's not null, will be matched to the severity from the violation
     * @return The number of rule violations
     */
    fun matchRule(event: AuditEvent, ruleName: String, expectedMessage: String?, expectedSeverity: String?): Boolean {
        if (ruleName != RuleUtil.getName(event)) {
            return false
        }

        // check message too, for those that have a specific one.
        // like GenericIllegalRegexp and Regexp
        if (expectedMessage != null) {
            // event.getMessage() uses java.text.MessageFormat in its implementation.
            // Read MessageFormat Javadoc about single quote:
            // http://java.sun.com/j2se/1.4.2/docs/api/java/text/MessageFormat.html
            val msgWithoutSingleQuote = StringUtils.replace(expectedMessage, "'", "")

            return expectedMessage == event.message || msgWithoutSingleQuote == event.message
        }
        // Check the severity. This helps to distinguish between
        // different configurations for the same rule, where each
        // configuration has a different severity, like JavadocMetod.
        // See also http://jira.codehaus.org/browse/MCHECKSTYLE-41
        return if (expectedSeverity != null) {
            expectedSeverity == event.severityLevel.getName()
        } else true

    }

    private fun doSeveritySummary(results: CheckstyleResults) {
        sink.section1()
        sink.sectionTitle1()
        sink.text(bundle.getString("report.checkstyle.summary"))
        sink.sectionTitle1_()

        sink.table()

        sink.tableRow()
        sink.tableHeaderCell()
        sink.text(bundle.getString("report.checkstyle.files"))
        sink.tableHeaderCell_()

        sink.tableHeaderCell()
        iconTool.iconInfo(IconTool.TEXT_TITLE)
        sink.tableHeaderCell_()

        sink.tableHeaderCell()
        iconTool.iconWarning(IconTool.TEXT_TITLE)
        sink.tableHeaderCell_()

        sink.tableHeaderCell()
        iconTool.iconError(IconTool.TEXT_TITLE)
        sink.tableHeaderCell_()
        sink.tableRow_()

        sink.tableRow()
        sink.tableCell()
        sink.text(results.fileCount.toString())
        sink.tableCell_()
        sink.tableCell()
        sink.text(results.getSeverityCount(SeverityLevel.INFO).toString())
        sink.tableCell_()
        sink.tableCell()
        sink.text(results.getSeverityCount(SeverityLevel.WARNING).toString())
        sink.tableCell_()
        sink.tableCell()
        sink.text(results.getSeverityCount(SeverityLevel.ERROR).toString())
        sink.tableCell_()
        sink.tableRow_()

        sink.table_()

        sink.section1_()
    }

    private fun doFilesSummary(results: CheckstyleResults) {
        sink.section1()
        sink.sectionTitle1()
        sink.text(bundle.getString("report.checkstyle.files"))
        sink.sectionTitle1_()

        sink.table()

        sink.tableRow()
        sink.tableHeaderCell()
        sink.text(bundle.getString("report.checkstyle.file"))
        sink.tableHeaderCell_()
        sink.tableHeaderCell()
        iconTool.iconInfo(IconTool.TEXT_ABBREV)
        sink.tableHeaderCell_()
        sink.tableHeaderCell()
        iconTool.iconWarning(IconTool.TEXT_ABBREV)
        sink.tableHeaderCell_()
        sink.tableHeaderCell()
        iconTool.iconError(IconTool.TEXT_ABBREV)
        sink.tableHeaderCell_()
        sink.tableRow_()

        // Sort the files before writing them to the report
        val fileList = ArrayList(results.files.keys)
        Collections.sort(fileList)

        for (filename in fileList) {
            val violations = results.getFileViolations(filename)
            if (violations.isEmpty()) {
                // skip files without violations
                continue
            }

            sink.tableRow()

            sink.tableCell()
            sink.link("#" + filename.replace('/', '.'))
            sink.text(filename)
            sink.link_()
            sink.tableCell_()

            sink.tableCell()
            sink.text(results.getSeverityCount(violations, SeverityLevel.INFO).toString())
            sink.tableCell_()

            sink.tableCell()
            sink.text(results.getSeverityCount(violations, SeverityLevel.WARNING).toString())
            sink.tableCell_()

            sink.tableCell()
            sink.text(results.getSeverityCount(violations, SeverityLevel.ERROR).toString())
            sink.tableCell_()

            sink.tableRow_()
        }

        sink.table_()
        sink.section1_()
    }

    private fun doDetails(results: CheckstyleResults) {

        sink.section1()
        sink.sectionTitle1()
        sink.text(bundle.getString("report.checkstyle.details"))
        sink.sectionTitle1_()

        // Sort the files before writing their details to the report
        val fileList = ArrayList(results.files.keys)
        Collections.sort(fileList)

        for (file in fileList) {
            val violations = results.getFileViolations(file)

            if (violations.isEmpty()) {
                // skip files without violations
                continue
            }

            sink.section2()
            val attrs = SinkEventAttributeSet()
            attrs.addAttribute(SinkEventAttributes.ID, file.replace('/', '.'))
            sink.sectionTitle(Sink.SECTION_LEVEL_2, attrs)
            sink.text(file)
            sink.sectionTitle_(Sink.SECTION_LEVEL_2)

            sink.table()
            sink.tableRow()
            sink.tableHeaderCell()
            sink.text(bundle.getString("report.checkstyle.column.severity"))
            sink.tableHeaderCell_()
            sink.tableHeaderCell()
            sink.text(bundle.getString("report.checkstyle.rule.category"))
            sink.tableHeaderCell_()
            sink.tableHeaderCell()
            sink.text(bundle.getString("report.checkstyle.rule"))
            sink.tableHeaderCell_()
            sink.tableHeaderCell()
            sink.text(bundle.getString("report.checkstyle.column.message"))
            sink.tableHeaderCell_()
            sink.tableHeaderCell()
            sink.text(bundle.getString("report.checkstyle.column.line"))
            sink.tableHeaderCell_()
            sink.tableRow_()

            doFileEvents(violations, file)

            sink.table_()
            sink.section2_()
        }

        sink.section1_()
    }

    private fun doFileEvents(eventList: List<AuditEvent>, filename: String) {
        for (event in eventList) {
            val level = event.severityLevel

            if (severityLevel != null && severityLevel == level) {
                continue
            }

            sink.tableRow()

            sink.tableCell()
            iconTool.iconSeverity(level.getName(), IconTool.TEXT_SIMPLE)
            sink.tableCell_()

            sink.tableCell()
            val category = RuleUtil.getCategory(event)
            if (category != null) {
                sink.text(category)
            }
            sink.tableCell_()

            sink.tableCell()
            val ruleName = RuleUtil.getName(event)
            if (ruleName != null) {
                sink.text(ruleName)
            }
            sink.tableCell_()

            sink.tableCell()
            sink.text(event.message)
            sink.tableCell_()

            sink.tableCell()

            val line = event.line
            if (xrefLocation != null && line != 0) {
                sink.link(xrefLocation + "/" + filename.replace("\\.java$".toRegex(), ".html") + "#L"
                        + line)
                sink.text(line.toString())
                sink.link_()
            } else if (line != 0) {
                sink.text(line.toString())
            }
            sink.tableCell_()

            sink.tableRow_()
        }
    }

    /**
     * Get the effective Checkstyle version at runtime.
     *
     * @return the MANIFEST implementation version of Checkstyle API package (can be `null`)
     */
    private fun getCheckstyleVersion(): String? = Configuration::class.java.`package`?.implementationVendor

    fun sortConfiguration(results: CheckstyleResults): List<ConfReference> {
        val result = ArrayList<ConfReference>()

        sortConfiguration(result, checkstyleConfig!!, null, results)

        Collections.sort(result)

        return result
    }

    private fun sortConfiguration(result: MutableList<ConfReference>,
                                  config: Configuration,
                                  parent: ChainedItem<Configuration>?,
                                  results: CheckstyleResults) {
        
        for (childConfig in config.children) {

            val ruleName = childConfig.name

            if (treeWalkerNames!!.contains(ruleName)) {

                // Special sub-case: TreeWalker is the parent of multiple rules, not an effective rule
                sortConfiguration(result, childConfig, ChainedItem(config, parent), results)

            } else {

                val fixedmessage = getConfigAttribute(
                        childConfig,
                        null,
                        "message",
                        null)

                // Grab the severity from the rule configuration, use null as default value
                val configSeverity = getConfigAttribute(
                        childConfig,
                        null,
                        "severity",
                        null)

                // count rule violations
                var violations: Long = 0
                var lastMatchedEvent: AuditEvent? = null
                for (errors in results.files.values) {
                    for (event in errors) {
                        if (matchRule(event, ruleName, fixedmessage, configSeverity)) {
                            lastMatchedEvent = event
                            violations++
                        }
                    }
                }

                if (violations > 0)
                // forget rules without violations
                {
                    val category = RuleUtil.getCategory(lastMatchedEvent!!)

                    result.add(ConfReference(category, childConfig, parent, violations, result.size))
                }
            }
        }
    }

    class ConfReference(val category: String,
                        val configuration: Configuration,
                        val parentConfiguration: ChainedItem<Configuration>?,
                        val violations: Long,
                        val count: Int) : Comparable<ConfReference> {

        /**
         * Standard compareTo method.
         */
        override fun compareTo(other: ConfReference): Int {

            var toReturn = this.category.compareTo(other.category)

            if (toReturn == 0) {
                toReturn = this.configuration.name.compareTo(other.configuration.name)
            }

            return if (toReturn != 0) {
                toReturn
            } else {
                other.count - this.count
            }
        }
    }

    class ChainedItem<T>(val value: T, val parent: ChainedItem<T>?)
}