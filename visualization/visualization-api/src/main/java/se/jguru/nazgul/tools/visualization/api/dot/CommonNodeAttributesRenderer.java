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
import se.jguru.nazgul.tools.visualization.model.diagram.statement.CommonNodeAttributes;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class CommonNodeAttributesRenderer extends AbstractStringRenderer<CommonNodeAttributes> {

    // Internal state
    private AttributeRenderer attributeRenderer;

    /**
     * Default constructor.
     */
    public CommonNodeAttributesRenderer() {

        // Delegate
        super(CommonNodeAttributes.class);

        // Assign internal state
        attributeRenderer = new AttributeRenderer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doRender(final RenderConfiguration config, final CommonNodeAttributes entity) {
        return config.getIndent()
                + entity.getId()
                + " " + attributeRenderer.render(config, entity.getAttributes());
    }
}
