package hydra;

import java.io.IOException;
import java.nio.file.*;

public class FtlRenamer extends Renamer {

    public FtlRenamer(Path target) {
        super(target);
    }

    public void apply() {
        String newName = target.toFile().getName().replace(".ftl.", ".");
        Path newPath = target.getParent().resolve(Paths.get(newName));

        try {
            Files.move(this.target, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("couldn't rename file: " + this.target);
        }
    }
}
