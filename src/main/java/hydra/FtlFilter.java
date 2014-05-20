package hydra;

import java.io.File;
import java.io.FileFilter;

public class FtlFilter implements FileFilter {
    public boolean accept(File pathname) {
        String base = pathname.getName();

        return base.contains(".ftl.");
    }
}
