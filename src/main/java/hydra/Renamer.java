package hydra;

import org.mockito.cglib.proxy.NoOp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

public class Renamer {

    protected Path target = null;

    public Renamer(Path target) {
        this.target = target;
    }

    public void apply() {
        // no-op that doesn't modify the file at all.
        // this is default behavior, and is specialized by derived classes.
    }
}
