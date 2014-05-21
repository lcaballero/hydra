package hydra;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopier implements IApplier {

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

    public void apply() {
        try {
            Files.copy(this.src, this.target, this.options);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasSource() {
        return Files.exists(src);
    }

    public boolean isDirectory() {
        return Files.isDirectory(src);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", src.toString(), target.toString());
    }
}
