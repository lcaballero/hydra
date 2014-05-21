package hydra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirCreator implements IApplier {

    private Path target;

    public DirCreator(Path target) {
        this.target = target;
    }

    @Override
    public void apply() {
        try {
            Files.createDirectory(this.target);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
