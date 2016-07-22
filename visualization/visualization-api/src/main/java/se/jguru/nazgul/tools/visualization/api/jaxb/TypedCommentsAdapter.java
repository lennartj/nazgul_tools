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

import se.jguru.nazgul.tools.visualization.api.diagram.Comment;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statement;
import se.jguru.nazgul.tools.visualization.api.diagram.statement.Statements;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * <p>JAXB marshalled entry for converting the default JAXB marshalled structure of a
 * Class-to-Comment Map into something a bit more sensible than the default Map transport form.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlTransient
public class TypedCommentsAdapter
        extends XmlAdapter<List<ClassKeyedComment>, Map<Class<? extends Statement>, Comment>> implements Serializable {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClassKeyedComment> marshal(final Map<Class<? extends Statement>, Comment> objectForm)
            throws Exception {

        // Handle nulls
        if(objectForm == null) {
            return null;
        }

        // All Done.
        return objectForm.entrySet()
                .stream()
                .map(c -> new ClassKeyedComment(c.getKey(), c.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Statement>, Comment> unmarshal(final List<ClassKeyedComment> transportForm)
            throws Exception {

        // Handle nulls
        if(transportForm == null) {
            return null;
        }

        // Convert
        final SortedMap<Class<? extends Statement>, Comment> toReturn =
                new TreeMap<>(Statements.DEFAULT_STATEMENT_COMPARATOR);
        transportForm.forEach(c -> toReturn.put(c.getResolvedClass(), c.getComment()));

        // All Done.
        return toReturn;
    }
}
