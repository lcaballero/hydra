package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseGeneratorTests extends FileHelpers {

    private Path src = Paths.get("files/sources/s6/WebDrop/");
    private Path root = Paths.get("files/targets/t6/WebDrop");

    @Before
    public void setup() throws IOException {
        new DirRemover(root.getParent()).apply();
        createDir(root.getParent());
        createDir(root);
    }

    @After
    public void teardown() {
        new DirRemover(root.getParent()).apply();
    }

    public void exists(Path r, String... files) {
        for (String s : files) {
            assertTrue(exists(r.resolve(s)));
        }
    }

    @Test
    public void should_should_create_target() throws IOException, TemplateException {
        Configuration config = new DefaultConfiguration(src.toFile());
        new Generator(src, root, config).apply();

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
            "src/main/resources/css",
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
