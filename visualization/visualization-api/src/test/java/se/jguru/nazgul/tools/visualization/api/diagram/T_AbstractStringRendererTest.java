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
package se.jguru.nazgul.tools.visualization.api.diagram;

import org.junit.Assert;
import org.junit.Test;
import se.jguru.nazgul.tools.visualization.api.AbstractStringRenderer;
import se.jguru.nazgul.tools.visualization.api.RenderConfiguration;
import se.jguru.nazgul.tools.visualization.api.dot.CommentRenderer;
import se.jguru.nazgul.tools.visualization.model.diagram.Comment;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class T_AbstractStringRendererTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullAcceptedType() {

        // Act & Assert
        new AbstractStringRenderer<String>(null) {
            @Override
            protected String doRender(final RenderConfiguration config, final String entity) {

                // Irrelevant
                return null;
            }
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullRenderConfiguration() {

        // Assemble
        final CommentRenderer commentRenderer = new CommentRenderer();

        // Act & Assert
        commentRenderer.render(null, new Comment("unimportant"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnNullEntity() {

        // Assemble
        final CommentRenderer commentRenderer = new CommentRenderer();

        // Act & Assert
        commentRenderer.render(new RenderConfiguration(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateExceptionOnUnrelatedEntity() {

        // Assemble
        final String notAComment = "NotAComment";
        final CommentRenderer commentRenderer = new CommentRenderer();

        // Act & Assert
        commentRenderer.render(new RenderConfiguration(), notAComment);
    }

    @Test
    public void validateAcceptance() {

        // Assemble
        final CommentRenderer commentRenderer = new CommentRenderer();

        // Act & Assert
        Assert.assertFalse(commentRenderer.accept("NotAComment"));
        Assert.assertFalse(commentRenderer.accept(null));
        Assert.assertTrue(commentRenderer.accept(new Comment("fooBar!")));
    }

    @Test
    public void validateStringForm() {

        // Assemble
        final CommentRenderer commentRenderer = new CommentRenderer();

        // Act & Assert
        Assert.assertEquals("[CommentRenderer]", commentRenderer.toString());
    }
}
