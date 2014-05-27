package hydra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Mkdirs implements IApplier {

    private Path target;

    public Mkdirs(Path target) {
        this.target = target;
    }

    @Override
    public void apply() {
        try {
            System.out.println("Mkdirs : " + this.target);
            if (Files.exists(this.target)) {
                System.out.println("Already Exists: " + this.target);
            } else {
                Files.createDirectories(this.target);
            }
        } catch (IOException e) {
            System.out.println("Failed to make directory: " + this.target);
            System.out.println(e.getMessage());
        }
    }
}
