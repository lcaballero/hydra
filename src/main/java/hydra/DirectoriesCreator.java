package hydra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoriesCreator implements IApplier {

    private Path target;

    public DirectoriesCreator(Path target) {
        this.target = target;
    }

    @Override
    public void apply() {
        try {
            Files.createDirectories(this.target);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
