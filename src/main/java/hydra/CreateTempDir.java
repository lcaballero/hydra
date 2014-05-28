package hydra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class CreateTempDir {

    public static final Path DEFAULT_TMP = Paths.get("/tmp/");
    public static final String DEFAULT_PREFIX = "hydra_den_";

    private Path tmp;
    private String prefix;

    public CreateTempDir() {
        this(null, null);
    }

    public CreateTempDir(Path tmp, String prefix) {
        this.tmp = tmp == null ? DEFAULT_TMP : tmp;
        this.prefix = prefix == null ? DEFAULT_PREFIX : prefix;
    }

    public Path apply() {
        String filename = this.prefix + UUID.randomUUID().toString();
        try {
            Path temp = Files.createDirectory(this.tmp.resolve(filename));
            System.out.printf("Creating temp directory: %s\n", temp);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
