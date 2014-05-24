package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractGenerator<TModel> implements IApplier {

    protected Path source;
    protected Path target;
    protected Configuration config;
    protected TModel model;

    public AbstractGenerator(Path source, Path target, Configuration config, TModel model) {
        this.source = source;
        this.target = target;
        this.config = config;
        this.model = model;
    }

    public Path to(String s) { return target.resolve(s); }
    public Path path(String s) { return Paths.get(s); }

    public BaseGenerator start() {
        return new BaseGenerator(source, target, config, model);
    }

    public abstract void apply() throws IOException, TemplateException;
}
