package hydra;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;


public class ZipExtractorTest extends FileHelpersForTesting {

    private Path src = Paths.get("files/sources/s8/UseHydra.jar");
    private Path target = Paths.get("files/targets/t8/");

    @Before public void setup() { createDir(target); }
    @After public void teardown() {
        new Rmdir(target).apply();
    }

    public void exists(Path r, String... files) {
        for (String s : files) {
            Path p = r.resolve(s);
            assertTrue("Path doesn't exists : " + p, exists(r.resolve(s)));
        }
    }

    @Test
    public void should_extract_files_from_the_jar() throws IOException {
        Path temp = new CreateTempDir(target, "hydra-den_").apply();
        new ZipExtractor(temp, src.toFile(), null).apply();

        exists(
            temp.resolve("template"),
            "WebDrop");

        exists(
            temp.resolve("template/WebDrop"),
            "app-nginx.conf",
            "hello-world.yml",
            "pom.ftl.xml",
            "startup.sh");

        exists(
            temp.resolve("template/WebDrop/src/main/java/webdrop/"),
            "App.ftl.java",
            "HelloWorldApplication.ftl.java",
            "HelloWorldConfiguration.ftl.java",
            "HelloWorldResource.ftl.java",
            "Main.ftl.java",
            "Saying.ftl.java",
            "TemplateHealthCheck.ftl.java");

        exists(
            temp.resolve("template/WebDrop/src/test/java/webdrop/"),
            "AppTest.ftl.java");

        exists(
            temp.resolve("template/WebDrop/src/main/resources/"),
            "css/base.css",
            "js/some-stuff.js",
            "pages/index.html");
    }
}
