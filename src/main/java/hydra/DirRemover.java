package hydra;

import java.nio.file.Path;

public class DirRemover implements IApplier {
    private Path target;

    public DirRemover(Path target) {
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
