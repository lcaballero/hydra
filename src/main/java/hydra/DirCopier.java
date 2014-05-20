package hydra;

import java.nio.file.Path;


public class DirCopier {

    private Path source;

    public DirCopier(Path root) {
        this.source = root;
    }

    public Path getPath() {
        return this.source;
    }

    public void copyToTarget(Path target) {
        new PostOrderTraverser()
            .preOrderTraversal(this.source.toFile())
            .toList()
            .stream()
            .map((file) ->
                new FileCopier(
                    file.toPath(),
                    target.resolve(this.source.relativize(file.toPath()))))
            .forEach((f) -> f.exec());
    }
}
