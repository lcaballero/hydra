package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class AppGeneratorTests extends FileHelpersForTesting {

    private Path src = Paths.get("files/sources/s7/WebDrop/");
    private Path root = Paths.get("files/targets/t7/WebDrop");
    private Map<String,Object> model = null;

    @Before
    public void setup() throws IOException {
        model = new HashMap<>();
        model.put("namespace", "com.hydra.den");

        new DirRemover(root.getParent()).apply();
        createDir(root.getParent());
        createDir(root);
    }

    @After
    public void teardown() {
        new DirRemover(root.getParent()).apply();
    }

    class NamespaceGenerator extends AbstractGenerator {

        NamespaceGenerator(Path source, Path target, Configuration config, Object model) {
            super(source, target, config, model);
        }

        public void apply() throws IOException, TemplateException {
            new BaseGenerator(source, target, config, model)
                .createDirs(
                    to("src/main/java/"))
                .createNamespaceDirs(
                    to("src/main/java/"),
                    "com.hydra.den")
                .apply();
        }
    }

    @Test
    public void should_create_namespace_dirs() throws IOException, TemplateException {
        Configuration config = new DefaultConfiguration(src);
        new NamespaceGenerator(src, root, config, model).apply();

        exists(
            root,
            "src/main/java/",
            "src/main/java/com/",
            "src/main/java/com/hydra/",
            "src/main/java/com/hydra/den/");
    }

    @Test
    public void should_should_create_target() throws IOException, TemplateException {
        Configuration config = new DefaultConfiguration(src);
        new AppGenerator(src, root, config, model).apply();

        assertThat(exists(root), is(true));

        exists(
            root,
            "src/",
            "src/main/",
            "src/main/java/",
            "src/main/java/webdrop/");

        exists(
            root,
            "src/test/",
            "src/test/java/",
            "src/test/java/webdrop/");

        exists(
            root,
            "src/main/resources/",
            "src/main/resources/css");

        Path ns = root.resolve("src/main/java/webdrop/");

        exists(
            ns,
            "App.java",
            "HelloWorldApplication.java",
            "HelloWorldConfiguration.java",
            "HelloWorldResource.java",
            "Main.java",
            "Saying.java",
            "TemplateHealthCheck.java");

        exists(
            root,
            "src/main/resources/css/base.css",
            "src/main/resources/js/some-stuff.js",
            "src/main/resources/pages/index.html");

        exists(
            root.resolve("src/test/java/webdrop/"),
            "AppTest.java");

        exists(
            root,
            "app-nginx.conf",
            "hello-world.yml",
            "startup.sh");

    }
}
