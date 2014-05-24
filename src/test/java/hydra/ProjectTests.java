package hydra;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ProjectTests extends FileHelpersForTesting {

    private Path s1 = Paths.get("files/sources/s9/WebDrop/");
    private Path t1 = Paths.get("files/targets/t9/WebDrop");

    private Path s2 = Paths.get("files/sources/s10/");
    private Path t2 = Paths.get("files/targets/t10/");

    @Before
    public void setup() throws IOException {
        new Rmdir(t1.getParent()).apply();
        createDir(t1.getParent());
        createDir(t1);

        new Rmdir(t2.getParent()).apply();
        createDir(t2.getParent());
        createDir(t2);
    }

    @After
    public void teardown() {
        new Rmdir(t1.getParent()).apply();
        new Rmdir(t2).apply();
    }

    @Test
    public void should_() {
        Path src = s2;
        Path target = t2;

        new Project(src, target).apply();

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

    @Test
    public void should_have_created_base_dirs() {

        Path src = s1;
        Path target = t1;

        new Project(src, target).apply();

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
