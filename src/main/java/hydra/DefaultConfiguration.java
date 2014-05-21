package hydra;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DefaultConfiguration extends Configuration {

    private Path dir;

    public DefaultConfiguration(Path dir) throws IOException {
        this.setDirectoryForTemplateLoading(dir.toFile());
        this.setObjectWrapper(new DefaultObjectWrapper());
        this.setDefaultEncoding("UTF-8");
        this.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        this.setIncompatibleImprovements(new Version(2,3,20));
    }

    public Path getDir() {
        return dir;
    }
}
