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
package se.jguru.nazgul.tools.visualization.api.jaxb;

import se.jguru.nazgul.tools.visualization.api.diagram.Graph;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>JAXB (un-)marshallable type for transporting a Class and its corresponding Statements.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlRootElement(namespace = Graph.NAMESPACE)
@XmlType(namespace = Graph.NAMESPACE, propOrder = {"statements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassKeyedStatements<T extends Statement> extends AbstractClassKeyedTransport<T> {

    @XmlElementWrapper
    @XmlElement(name = "statement")
    private List<Statement> statements;

    /**
     * Default constructor, reserved for framework use.
     */
    public ClassKeyedStatements() {
    }

    /**
     * Compound constructor creating a {@link ClassKeyedStatements} instance wrapping the supplied data.
     *
     * @param statementType The type of Statement.
     * @param statements The List of Statements to transport.
     */
    public ClassKeyedStatements(final Class<T> statementType, final List<Statement> statements) {

        super(statementType);

        // Check sanity
        this.statements = statements == null ? new ArrayList<>() : statements;
    }

    /**
     * Retrieves the List of Statements from this {@link ClassKeyedStatements} instance.
     *
     * @return the List of wrapped Statements.
     */
    public List<Statement> getStatements() {
        return statements;
    }
}
