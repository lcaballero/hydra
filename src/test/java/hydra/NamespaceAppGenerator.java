package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NamespaceAppGenerator extends AbstractGenerator {

    public NamespaceAppGenerator(Path source, Path target, Configuration config, Object model) {
        super(source, target, config, model);
    }

    public Path to(String s) { return target.resolve(s); }
    public Path path(String s) { return Paths.get(s); }

    @Override
    public void apply() throws IOException, TemplateException {

        start()
            .createDir(target)
            .createDirs(
                to("src/"),
                to("src/main/"),
                to("src/main/java/"),
                to("src/main/java/webdrop/"),
                to("src/main/resources/"),
                to("src/main/resources/css/"),
                to("src/main/resources/js/"),
                to("src/main/resources/pages/"),
                to("src/test/"),
                to("src/test/java/"),
                to("src/test/java/webdrop/"))
            .process(
                path("src/main/java/webdrop/App.ftl.java"),
                to("src/main/java/webdrop/App.java"))
            .process(
                path("src/main/java/webdrop/"),
                "HelloWorldApplication.ftl.java",
                "HelloWorldConfiguration.ftl.java",
                "HelloWorldResource.ftl.java",
                "Main.ftl.java",
                "Saying.ftl.java",
                "TemplateHealthCheck.ftl.java")
            .process(
                path("pom.ftl.xml"),
                to("pom.xml"))
            .process(
                path("src/test/java/webdrop/AppTest.ftl.java"),
                to("src/test/java/webdrop/AppTest.java"))
            .copy("src/main/resources/css/base.css")
            .copy("src/main/resources/js/some-stuff.js")
            .copy("src/main/resources/pages/index.html")
            .copy("app-nginx.conf")
            .copy("hello-world.yml")
            .copy("startup.sh")
            .apply();
    }
}

