package hydra;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class ProjectGen<TModel> implements IApplier {

    protected Path source;
    protected Path target;
    protected List<IApplier> exec = new ArrayList<>();
    protected Configuration config;
    protected TModel model;
    protected Function<String,String> renamer = ProjectGen::removeFtl;

    private ProjectGen(Path source, Path target, TModel model, Function<String,String> renamer) {
        this.source = source;
        this.target = target;
        this.model = model;
        this.config = new DefaultConfiguration(source);
        this.renamer = renamer;
    }

    public Path from(String s) { return source.resolve(s); }
    public Path to(String s) { return target.resolve(s); }
    public Path path(String s) { return Paths.get(s); }

    public ProjectGen<TModel> createDirs(String... dirs) {
        for (String dir : dirs) {
            Path d = target.resolve(dir);
            System.out.println("Creating Dir: " + d);
            exec.add(new Mkdirs(d));
        }
        return this;
    }

    /**
     * Creates a new instance with the target and source resolved to the new
     * path.
     *
     * @param path A path that maps to a location in both the target and source.
     * @return An instance with source and target resolved to the given path.
     */
    public ProjectGen<TModel> in(String path) {
        ProjectGen<TModel> gen = of(source.resolve(path), target.resolve(path), model);
        exec.add(gen);
        return gen;
    }

    public Path namespaceDirs(String ns) {
        String[] dirs = (ns == null ? "" : ns).split("[.]");
        Path t = Paths.get("");
        for (String d : dirs) {
            t = t.resolve(d);
        }
        return t;
    }

    public ProjectGen<TModel> toNamespace(String src, Object ns) {
        Path t = namespaceDirs(ns.toString());
        System.out.println("Namespace dirs: " + t);
        Path nsDirs = target.resolve(t);
        ProjectGen<TModel> gen = of(source.resolve(src), nsDirs, model).mkdir();

        return gen;
    }

    public String process(Configuration cfg, TModel model, Path ftl) {
        StringWriter result = new StringWriter();

        Template tpl = null;
        try {
            tpl = cfg.getTemplate(ftl.toString());
            tpl.process(model, result);
        } catch (TemplateException | IOException e) {
            System.out.printf("source: %s, target: %s, ftl: %s\n", source, target, ftl);
            e.printStackTrace();
        }

        return result.toString();
    }

    public static String removeFtl(String s) {
        return (s == null ? "" : s).replace(".ftl.", ".");
    }

    public ProjectGen<TModel> mkdir() {
        exec.add(new Mkdirs(this.target));
        return this;
    }

    public ProjectGen<TModel> translate(String a, String b) {
        exec.add(new WriteContent(process(config, model, Paths.get(a)), target.resolve(b)));
        return this;
    }

    public ProjectGen<TModel> process(String... b) {
        Function<String,String> fn = (this.renamer == null) ? (s) -> s : this.renamer;
        for (String s : b) {
            String t = fn.apply(s);
            exec.add(new WriteContent(process(config, model, Paths.get(s)), target.resolve(t)));
        }
        return this;
    }

    public static <TModel> ProjectGen<TModel> of(Path source, Path target, TModel model) {
        return new ProjectGen<>(source, target, model, ProjectGen::removeFtl);
    }

    public ProjectGen<TModel> apply(IApplier applier) {
        this.exec.add(applier);
        return this;
    }

    public ProjectGen<TModel> copy(String a) {
        exec.add(new FileCopier(from(a), to(a)));
        return this;
    }

    public ProjectGen<TModel> apply(Function<ProjectGen<TModel>, ProjectGen<TModel>> fn) {
        this.exec.add(fn.apply(of(source, target, model)));
        return this;
    }

    public void apply() {
        for (IApplier a : exec) {
            a.apply();
        }
    }
}
