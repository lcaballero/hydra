package hydra;

import java.nio.file.Path;

public class Renamer {

    protected Path target = null;

    public Renamer(Path target) {
        this.target = target;
    }

    public Path getTarget() {
        return this.target;
    }

    public void apply() {
        // no-op that doesn't modify the file at all.
        // this is default behavior, and is specialized by derived classes.
    }
}
