package hydra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateTempDir {

    public static final Path DEFAULT_TMP = Paths.get("/tmp/");
    public static final String DEFAULT_PREFIX = "hydra-den_";

    private Path tmp;
    private String prefix;

    public CreateTempDir() {
        this(null, null);
    }

    public CreateTempDir(Path tmp, String prefix) {
        this.tmp = tmp == null ? DEFAULT_TMP : tmp;
        this.prefix = prefix == null ? DEFAULT_PREFIX : prefix;
    }

    public Path apply() throws IOException {
        Path temp = Files.createTempDirectory(this.tmp, this.prefix);
        System.out.printf("Creating temp directory: %s\n", temp);

        return temp;
    }

}
