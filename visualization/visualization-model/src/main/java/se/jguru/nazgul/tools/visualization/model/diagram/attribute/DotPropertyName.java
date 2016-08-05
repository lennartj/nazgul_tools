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
public @interface DotPropertyName {

    /**
     * Specifies the name of the DOT configuration property.
     */
    String name() default "##default";
}
