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
package se.jguru.nazgul.tools.visualization.api.dot;

import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonEdgeAttributes;

/**
 * <p>Attribute statement implementation, corresponding to the following DOT grammar:</p>
 * <pre>
 *     attr_stmt : edge attr_list
 * </pre>
 * <p>Typically renders into something like the following:</p>
 * <pre>
 *     edge [	fontname = "Helvetica-Oblique",
 *              fontsize = "36",
 *              label = "\n\n\n\nObject Oriented Graphs\nStephen North, 3/19/93",
 *              size = "6,6" ];
 * </pre>
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 * @see se.jguru.nazgul.tools.visualization.model.diagram.attribute.EdgeAttributes
 */
public class CommonEdgeAttributesRenderer extends AbstractStringRenderer<CommonEdgeAttributes> {

    // Internal state
    private AttributeRenderer attributeRenderer;

    /**
     * Default constructor.
     */
    public CommonEdgeAttributesRenderer() {

        // Delegate
        super(CommonEdgeAttributes.class);

        // Assign internal state
        this.attributeRenderer = new AttributeRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final CommonEdgeAttributes entity) {
        return config.getIndent()
                + entity.getId()
                + " " + attributeRenderer.render(config, entity.getAttributes());
    }
}
