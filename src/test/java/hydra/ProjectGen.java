package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class ProjectGen {

    protected Path source;
    protected Path target;
    protected List<IApplier> exec = new ArrayList<>();
    protected Configuration config;
    protected Object model;

    private ProjectGen(Path source, Path target, List<IApplier> exec, Configuration config, Object model) {
        this.source = source;
        this.target = target;
        this.exec = exec;
        this.config = config;
        this.model = model;
    }

    public Path to(String s) { return target.resolve(s); }
    public Path path(String s) { return Paths.get(s); }

    public ProjectGen createDirs(String... dirs) {
        for (String dir : dirs) {
            exec.add(new DirectoriesCreator(target.resolve(dir)));
        }
        return this;
    }

    public static ProjectGen of(Path source, Path target, List<IApplier> exec, Configuration config, Object model) {
        return new ProjectGen(source, target, exec, config, model);
    }

    public ProjectGen apply(IApplier applier) {
        this.exec.add(applier);
        return this;
    }

    public ProjectGen apply(Function<ProjectGen, ProjectGen> fn) {
        return fn.apply(this);
    }

    public void apply() throws IOException, TemplateException {
        for (IApplier a : exec) {
            a.apply();
        }
    }
}
