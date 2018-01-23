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

import org.eclipse.persistence.jaxb.JAXBContextFactory
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource

/**
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
class CheckstyleReportTest {

    // Shared state
    private val JAXB_FACTORY_CLASSNAME_PROPERTY = "javax.xml.bind.context.factory"
    private val NEWLINE = System.getProperty("line.separator")

    private lateinit var marshaller: Marshaller
    private lateinit var unmarshaller: Unmarshaller

    @Before
    fun setupSharedState() {

        System.setProperty(
                JAXB_FACTORY_CLASSNAME_PROPERTY,
                JAXBContextFactory::class.java.name)

        // Create the JAXB context and marshaller
        val ctx = JAXBContext.newInstance(CheckstyleReport::class.java)

        marshaller = ctx.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8")
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

        unmarshaller = ctx.createUnmarshaller()
    }

    @After
    fun teardownSharedState() {

        System.clearProperty(JAXB_FACTORY_CLASSNAME_PROPERTY)
    }

    @Test
    fun validateUnmarshallingXMLReport() {

        // Assemble
        val data = readFully("testdata/resultfiles/checkstyle-result_1.xml")
        println("Read ${data.length} chars from resourcePath.")

        // Act
        val resurrected = unmarshaller.unmarshal(StreamSource(StringReader(data)), CheckstyleReport::class.java).value

        // Assert
        Assert.assertNotNull(resurrected)

        val files = resurrected.file
        Assert.assertNotNull(files)
        Assert.assertEquals(6, files.size)

        files.forEachIndexed { index, current -> println(" [${index}]: name ${current.name}") }

    }

    //
    // Private helpers
    //

    private fun readFully(resourcePath: String): String {

        val classLoader = this.javaClass.classLoader
        val theReader = BufferedReader(InputStreamReader(classLoader.getResourceAsStream(resourcePath)))

        Assert.assertNotNull(theReader)
        val builder = StringBuilder()

        for (aLine: String in theReader.lines()) {
            builder.append(aLine).append(NEWLINE)
        }

        return builder.toString()
    }
}
