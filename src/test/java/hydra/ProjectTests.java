package hydra;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ProjectTests extends FileHelpersForTesting {

    private Path src = Paths.get("files/sources/s9/WebDrop/");
    private Path target = Paths.get("files/targets/t9/WebDrop");

    @Before
    public void setup() throws IOException {
        new Rmdir(target.getParent()).apply();
        createDir(target.getParent());
        createDir(target);
    }

    @After
    public void teardown() {
        new Rmdir(target.getParent()).apply();
    }

    @Test
    public void should_have_created_base_dirs() {
        new Project().apply();

        exists(target);

        exists(
            target,
            "src/",
            "src/main/",
            "src/main/java/",
            "src/main/java/com/hydra/den/",
            "src/main/resources/css/",
            "src/main/resources/js/",
            "src/main/resources/pages/",
            "src/test/java/com/hydra/den/"
        );

        exists(
            target.resolve("src/main/java/com/hydra/den/"),
            "App.java",
            "HelloWorldApplication.java",
            "HelloWorldConfiguration.java",
            "HelloWorldResource.java",
            "Main.java",
            "TemplateHealthCheck.java");

        exists(target.resolve("src/test/java/com/hydra/den/"), "AppTest.java");

        exists(
            target,
            "src/main/resources/css/base.css",
            "src/main/resources/js/some-stuff.js",
            "src/main/resources/pages/index.html",
            "pom.xml",
            "app-nginx.conf",
            "hello-world.yml",
            "startup.sh");
    }
}
