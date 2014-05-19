package hydra;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopier {

    public static final CopyOption[] DEFAULT_COPY_OPTIONS = new CopyOption[] {
        StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING
    };

    protected Path src;
    protected Path target;
    protected CopyOption[] options;

    public FileCopier(Path src, Path target, CopyOption... options) {
        this.src = src;
        this.target = target;
        this.options = options == null || options.length == 0 ? DEFAULT_COPY_OPTIONS : options;
    }

    public void exec() throws IOException {
        Files.copy(this.src, this.target, this.options);
    }

    public boolean hasSource() {
        return src.toFile().exists();
    }

    public boolean isDirectory() {
        return src.toFile().isDirectory();
    }
}
