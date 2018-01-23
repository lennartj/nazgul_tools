/*-
 * #%L
 * Nazgul Project: nazgul-tools-checkstyle-maven-plugin
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

package se.jguru.nazgul.tools.plugin.checkstyle.report.model

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType

/**
 * JAXB-annotated model for Checkstyle XML reports.
 * 
 * Typically, XML reports are on the form:
 * <pre>
 *     <code>
 *         &lt;checkstyle version="6.19"&gt;
 *             &lt;file name="/Users/lj/Development/Projects/Nazgul/nazgul-core/core/jmx/jmx-api/src/main/java/se/jguru/nazgul/core/jmx/api/AbstractMBean.java"&gt;
 *                 &lt;error line="288" severity="warning" message="Line is longer than 120 characters (found 131)."
 *                 source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/&gt;
 *                 &lt;error line="364" column="81" severity="warning" message="&apos;+&apos; should be on a new line."
 *                 source="com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck"/&gt;
 *                 &lt;error line="403" severity="warning" message="Line is longer than 120 characters (found 123)."
 *                 source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/&gt;
 *             &lt;/file&gt;
 *         &lt;/checkstyle&gt;
 *     </code>
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlRootElement
@XmlType(name = "checkstyle")
@XmlAccessorType(XmlAccessType.FIELD)
class CheckstyleReport(@XmlElement val file : List<CheckstyleFile>) : Serializable {

        /**
         * JAXB-friendly constructor.
         */
        constructor() : this(mutableListOf<CheckstyleFile>())
}

/**
 * The file element of a Checkstyle XML report, which mainly contains the
 * name attribute in addition to child elements containing actual Checkstyle information.
 */
@XmlType(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
class CheckstyleFile(@XmlElement(required = true) val name : String,
                     @XmlElement val error : List<CheckstyleError>) : Serializable {

        /**
         * JAXB-friendly constructor.
         */
        constructor() : this("unknown", mutableListOf())
}

/**
 * The file element of a Checkstyle XML report, which mainly contains the
 * name attribute in addition to child elements containing actual Checkstyle information.
 *
 * line="364"
 * column="81"
 * severity="warning"
 * message="&apos;+&apos; should be on a new line."
 * source="com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck"
 */
@XmlType(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
class CheckstyleError(@XmlAttribute(required = true) val line : Int,
                      @XmlAttribute(required = true) val column : Int,
                      @XmlAttribute(required = true) val severity : String,
                      @XmlAttribute val message : String,
                      @XmlAttribute val source : String) : Serializable {

        /**
         * JAXB-friendly constructor.
         */
        constructor() : this(-1, -1, "unknown", "unknown", "unknown")
}
