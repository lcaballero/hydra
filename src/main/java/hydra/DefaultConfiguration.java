package hydra;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;

public class DefaultConfiguration extends Configuration {

    public DefaultConfiguration(File dir) throws IOException {
        this.setDirectoryForTemplateLoading(dir);
        this.setObjectWrapper(new DefaultObjectWrapper());
        this.setDefaultEncoding("UTF-8");
        this.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        this.setIncompatibleImprovements(new Version(2,3,20));
    }
}
