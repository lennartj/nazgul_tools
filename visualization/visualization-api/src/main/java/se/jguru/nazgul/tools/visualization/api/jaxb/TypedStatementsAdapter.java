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
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>JAXB marshalled entry for converting the default JAXB marshalled structure of a
 * Class-to-List_of_Statement Map into something a bit more sensible than the default Map transport form.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlTransient
public class TypedStatementsAdapter extends XmlAdapter<List<ClassKeyedStatements<? extends Statement>>,
        Map<Class<? extends Statement>, List<Statement>>> implements Serializable {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Statement>, List<Statement>> unmarshal(
            final List<ClassKeyedStatements<? extends Statement>> transportForm) throws Exception {

        // Handle nulls
        if (transportForm == null) {
            return null;
        }

        final Map<Class<? extends Statement>, List<Statement>> toReturn = new TreeMap<>(
                Statements.DEFAULT_STATEMENT_COMPARATOR);
        transportForm.forEach(c -> toReturn.put(c.getResolvedClass(), c.getStatements()));

        // All Done.
        return toReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClassKeyedStatements<? extends Statement>> marshal(
            final Map<Class<? extends Statement>, List<Statement>> objectForm) throws Exception {

        // Handle nulls
        if(objectForm == null) {
            return null;
        }

        final List<ClassKeyedStatements<? extends Statement>> toReturn = new ArrayList<>();
        objectForm.entrySet().forEach(c -> toReturn.add(new ClassKeyedStatements<>(c.getKey(), c.getValue())));

        // All Done.
        return toReturn;
    }
}
