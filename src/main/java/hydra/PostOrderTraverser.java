package hydra;

import com.google.common.collect.TreeTraverser;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;


public class PostOrderTraverser extends TreeTraverser<File> {
    @Override
    public Iterable<File> children(File root) {
        if (root.isFile()) {
            return Collections.EMPTY_LIST;
        } else {
            return Arrays.asList(root.listFiles());
        }
    }
}

