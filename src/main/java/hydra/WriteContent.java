package hydra;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.IOException;
import java.nio.file.Path;


public class WriteContent implements IApplier {

    private Path target;
    private String content;

    public WriteContent(Path target, String content) {
        this.target = target;
        this.content = content;
    }

    @Override
    public void apply() {
        try {
            Files.write(content, target.toFile(), Charsets.UTF_8);
        } catch (IOException e) {
            System.out.println(this.getClass().getName() + " : " + e.getMessage());
        }
    }
}
