package hydra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelpers {

    public boolean exists(Path p) {
        return Files.exists(p);
    }

    public Path path(String path) {
        return Paths.get(path);
    }

    public void createDir(Path p) {
        try {
            if (!p.toFile().exists()) {
                Files.createDirectory(p);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createFile(Path p) {
        try {
            if (!p.toFile().exists()) {
                Files.createFile(p);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The assumption here is that the directory is already empty.
     * @param p The empty dir to delete.
     */
    public void delete(Path p) {
        try {
            if (Files.exists(p)) {
                Files.delete(p);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void copy(Path a, Path b) {
        try {
            Files.copy(a, b, FileCopier.DEFAULT_COPY_OPTIONS);
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
