package hydra;

import java.nio.file.Path;

public class Rmdir implements IApplier {
    private Path target;

    public Rmdir(Path target) {
        this.target = target;
    }

    public void apply() {
        new FileTraverser()
            .postOrderTraversal(this.target.toFile())
            .toList()
            .stream()
            .forEach((f) -> f.delete())
        ;
    }
}
