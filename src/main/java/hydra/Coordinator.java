package hydra;

import java.nio.file.Path;
import java.util.HashMap;

public class Coordinator {

    /**
     * An instance of a Coordinator carries out the file and code generation,
     * running template translation, etc.
     *
     * @param config A file holding any system configuration data, like the
     *               location of generator repo, gitignore file store,
     *               license store, etc.
     * @param to A directory to place all generated files.
     */
    public Coordinator(Path config, Path to) {

    }

    public void apply() {
        // The interface will be evented with items from the source directory
        // and the resulting instances will be accumulated and the processed.

        // 1. Handle all of the paths pumped
    }

    public Renamer rename(Path p) {
        if (p.toFile().getName().contains(".ftl.")) {
            return new FtlRenamer(p);
        }
        else {
            return new Renamer(p);
        }
    }

    public FileCopier copyFile(Path src, Path target) {
        if (target.toFile().getName().contains(".ftl.")) {
            return new FileCopier(src, target);
        }
        else {
            return new FileCopier(src, target);
        }
    }

    public FreeMarkerProcessor process(Path p) {
        if (p.toFile().getName().contains(".ftl.")) {
            return new FreeMarkerProcessor(new HashMap<>(), p.toFile());
        }
        else {
            return new FreeMarkerProcessor(new HashMap<>(), p.toFile());
        }

    }
}
