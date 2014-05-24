package hydra;

import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class FileHelpersForTesting extends FileHelpers {

    public void exists(Path r, String... files) {
        for (String s : files) {
            Path p = r.resolve(s);
            assertTrue("Path doesn't exists : " + p, exists(r.resolve(s)));
        }
    }
}
