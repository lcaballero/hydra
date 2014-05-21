package hydra;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generator implements IApplier {

    private Path source;
    private Path target;
    private List<IApplier> exec = new ArrayList<>();
    private Configuration config;

    public Generator(Path source, Path target, Configuration config) {
        this.source = source;
        this.target = target;
        this.config = config;
    }

    public Generator createDir(Path... dirs) {
        for (Path p : dirs) {
            exec.add(new DirCreator(p));
        }
        return this;
    }

    public boolean exists(String s) {
        return Files.exists(to(s));
    }

    public Generator addDirsTo(String dir, String... subs) {
        Path root = to(dir);
        if (!Files.exists(root)) {
            createDir(root);
        }
        for (String sub : subs) {
            createDir(root.resolve(sub));
        }
        return this;
    }

    public Generator createDirs(Path... dirs) {
        for (Path p : dirs) {
            exec.add(new DirectoriesCreator(p));
        }
        return this;
    }

    public String process(Configuration cfg, Object model, Path ftl) throws IOException, TemplateException {
        StringWriter result = new StringWriter();

        Template tpl = cfg.getTemplate(ftl.toFile().toString());
        tpl.process(model, result);

        return result.toString();
    }

    public Generator process(Object model, Path a, Path b) throws IOException, TemplateException {
        exec.add(new WriteContent(b, process(config, model, a)));
        return this;
    }

    public Path to(String s) { return target.resolve(s); }
    public Path from(String s) { return source.resolve(s); }
    public Path path(String s) { return Paths.get(s); }

    public Generator build() throws IOException, TemplateException {
        Map<String,Object> model = new HashMap<>();
        model.put("namespace", "com.hydra.den");

        return new Generator(source, target, config)
            .createDir(target)
            .createDirs(
                to("src/main/java/webdrop/"),
                to("src/test/java/webdrop/"))
            .addDirsTo(
                "src/main/resources/",
                    "css/",
                    "js/",
                    "pages/")
            .process(
                model,
                path("src/main/java/webdrop/App.ftl.java"),
                path("src/main/java/webdrop/App.java"))
            .translate(
                model,
                path("src/main/java/webdrop/"),
                "HelloWorldApplication.ftl.java",
                "HelloWorldConfiguration.ftl.java",
                "HelloWorldResource.ftl.java",
                "Main.ftl.java",
                "Saying.ftl.java",
                "TemplateHealthCheck.ftl.java");
    }

    public Generator translate(Object model, Path dir, String... a) throws IOException, TemplateException {
        for (String f : a) {
            process(
                model,
                dir.resolve(f),
                dir.resolve(f.replace(".ftl.", ".")));
        }
        return this;
    }

    public void apply() {
        exec.stream()
            .forEach(IApplier::apply);
    }
}
