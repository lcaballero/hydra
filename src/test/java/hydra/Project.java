package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import sun.jvm.hotspot.debugger.ia64.IA64ThreadContext;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Project {

    public void apply() throws IOException, TemplateException {

        Path source = Paths.get("");
        Path target = Paths.get("Project");
        Path temp = source;
        Configuration config = new DefaultConfiguration(temp);
        List<IApplier> execs = new ArrayList<IApplier>();
        Object model = new HashMap<String,Object>();

        ProjectGen.of(source, target, execs, config, model)
            .apply((p) ->
                p.createDirs(
                    "src/main/java/webdrop/",
                    "src/main/resources/css/",
                    "src/main/resources/js/",
                    "src/main/resources/pages/",
                    "src/test/java/webdrop/"
                )
            )
        .apply();
    }
}
