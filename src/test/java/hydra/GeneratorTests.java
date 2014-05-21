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

public class GeneratorTests extends FileHelpers {

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
//        new DirRemover(root.getParent()).apply();
    }

    @Test
    public void should_should_create_target() throws IOException, TemplateException {
        Configuration config = new DefaultConfiguration(src.toFile());
        new Generator(src, root, config).build().apply();

        assertThat(exists(root), is(true));

        assertThat(exists(root.resolve("src/")), is(true));
        assertThat(exists(root.resolve("src/main/")), is(true));
        assertThat(exists(root.resolve("src/main/java/")), is(true));
        assertThat(exists(root.resolve("src/main/java/webdrop/")), is(true));

        assertThat(exists(root.resolve("src/test/")), is(true));
        assertThat(exists(root.resolve("src/test/java/")), is(true));
        assertThat(exists(root.resolve("src/test/java/webdrop/")), is(true));

        assertThat(exists(root.resolve("src/main/resources/")), is(true));
        assertThat(exists(root.resolve("src/main/resources/css")), is(true));
        assertThat(exists(root.resolve("src/main/resources/css")), is(true));

        Path ns = root.resolve("src/main/java/webdrop/");
        assertThat(exists(ns.resolve("App.java")), is(true));
//        assertThat(exists(ns.resolve("HelloWorldApplication.java")), is(true));
    }
}
