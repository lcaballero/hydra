package hydra;

import java.nio.file.Path;


public class DirCopier implements IApplier {

    private Path source;
    private Path target;

    public DirCopier(Path root, Path target) {
        this.source = root;
        this.target = target;
    }

    public Path getPath() {
        return this.source;
    }
    public Path getTarget() { return this.target; }

    public void apply() {
        new FileTraverser()
            .preOrderTraversal(this.source.toFile())
            .toList()
            .stream()
            .map((file) ->
                new FileCopier(
                    file.toPath(),
                    target.resolve(this.source.relativize(file.toPath()))))
            .forEach((f) -> f.apply());
    }
}