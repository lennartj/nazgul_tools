/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
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


package se.jguru.nazgul.tools.visualization.model.jaxb;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlRootElement(namespace = AbstractIdentifiable.NAMESPACE)
@XmlType(namespace = AbstractIdentifiable.NAMESPACE, propOrder = {"items"})
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericRoot {

    @XmlElementWrapper
    @XmlElement(name = "item")
    private List<Object> items;

    public GenericRoot() {
        items = new ArrayList<>();
    }

    public GenericRoot(final Object ... toAdd) {
        this();

        add(toAdd);
    }

    public List<Object> getItems() {
        return items;
    }

    public void add(final Object ... items) {
        Collections.addAll(this.items, items);
    }
}
