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


package se.jguru.nazgul.tools.visualization.model.diagram.attribute;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The name of the configuration property, if it differs
 * from the attribute name given in
 * <a href="http://www.graphviz.org/content/attrs">DOT/Graphviz</i></a>.</p>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD})
public @interface DotProperty {

    /**
     * Specifies the name of the DOT configuration property.
     */
    String name() default "##default";

    /**
     * Indicates if the Field annotated with @DotProperty requires special treatment
     * in resolving its dot property equivalent. For example, when two DOT configuration properties are collected
     * into a single model value.
     */
    boolean specialTreatment() default false;
}
