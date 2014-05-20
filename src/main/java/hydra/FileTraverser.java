package hydra;

import com.google.common.collect.TreeTraverser;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;


public class FileTraverser extends TreeTraverser<File> {
    @Override
    public Iterable<File> children(File root) {
        if (root.isFile()) {
            return Collections.EMPTY_LIST;
        } else {
            File[] files = root.listFiles();
            if (files != null && files.length > 0) {
                return Arrays.asList(files);
            } else {
                return Collections.EMPTY_LIST;
            }
        }
    }
}

