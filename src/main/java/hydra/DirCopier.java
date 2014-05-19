package hydra;

import java.nio.file.CopyOption;
import java.nio.file.Path;

public class DirCopier extends FileCopier {

    public DirCopier(Path src, Path target, CopyOption... options) {
        super(src, target, options);
    }
}
