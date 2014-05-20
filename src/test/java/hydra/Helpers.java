package hydra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helpers {

    public void create(Path p) {
        try {
            if (!p.toFile().exists()) {
                Files.createFile(p);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasFiles(Path p) {
        File f = p.toFile();
        File[] files = f.listFiles();
        return files != null && files.length > 0;
    }
}
