package se.jguru.nazgul.tools.visualization.impl.doclet.helpers;

import javax.tools.SimpleJavaFileObject;
import java.io.File;
import java.net.URI;

/**
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
public class UrlJavaFileObject extends SimpleJavaFileObject {

    public UrlJavaFileObject(final URI uri, final Kind kind) {
        super(uri, kind);
    }

    public static UrlJavaFileObject createForSourceFile(final File aFile) {
        return new UrlJavaFileObject(aFile.toURI(), Kind.SOURCE);
    }

    public static UrlJavaFileObject createForClassFile(final File aFile) {
        return new UrlJavaFileObject(aFile.toURI(), Kind.SOURCE);
    }
}
