package hydra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class NewName extends Renamer {

    private String newName = null;

    public NewName(Path target, String name) {
        super(target);
        this.newName = name;
    }

    @Override
    public void apply() {
        Path newPath = this.target.getParent().resolve(Paths.get(newName));

        try {
            Files.move(this.target, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("couldn't rename file: " + this.target);
        }
    }
}
