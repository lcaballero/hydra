package hydra;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BaseGenerator implements IApplier {

    private Path source;
    private Path target;
    private List<IApplier> exec = new ArrayList<>();
    private Configuration config;
    private Object model;

    public BaseGenerator(Path source, Path target, Configuration config, Object model) {
        this.source = source;
        this.target = target;
        this.config = config;
        this.model = model;
    }

    public BaseGenerator createDir(Path... dirs) {
        for (Path p : dirs) {
            exec.add(new DirCreator(p));
        }
        return this;
    }

    public boolean exists(String s) {
        return Files.exists(to(s));
    }

    public BaseGenerator addDirsTo(String dir, String... subs) {
        Path root = to(dir);
        if (!Files.exists(root)) {
            createDir(root);
        }
        for (String sub : subs) {
            createDir(root.resolve(sub));
        }
        return this;
    }

    public BaseGenerator createDirs(Path... dirs) {
        for (Path p : dirs) {
            exec.add(new DirectoriesCreator(p));
        }
        return this;
    }

    public String process(Configuration cfg, Object model, Path ftl) throws IOException, TemplateException {
        StringWriter result = new StringWriter();

        Template tpl = cfg.getTemplate(ftl.toString());
        tpl.process(model, result);

        return result.toString();
    }

    public BaseGenerator process(Path a, Path b) throws IOException, TemplateException {
        exec.add(new WriteContent(process(config, model, a), b));
        return this;
    }

    public Path to(String s) {
        return target.resolve(s);
    }

    public Path from(String s) {
        return source.resolve(s);
    }

    public BaseGenerator copy(String a) {
        exec.add(new FileCopier(from(a), to(a)));
        return this;
    }

    public BaseGenerator process(Path dir, String... a) throws IOException, TemplateException {
        for (String f : a) {
            process(
                dir.resolve(f),
                target.resolve(dir.resolve(f.replace(".ftl.", "."))));
        }
        return this;
    }

    public BaseGenerator createNamespaceDirs(Path base, String namespace) {
        String[] dirs = namespace.split("\\.");

        for (String dir : dirs) {
            base = base.resolve(dir);
            createDir(base);
        }

        return this;
    }

    public void apply() throws IOException, TemplateException {
        for (IApplier a : exec) {
            a.apply();
        }
    }
}
