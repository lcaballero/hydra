package hydra;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Generator implements IApplier {

    private Path source;
    private Path target;
    private Configuration config;

    public Generator(Path source, Path target, Configuration config) {
        this.source = source;
        this.target = target;
        this.config = config;
    }

    public Path to(String s) { return target.resolve(s); }
    public Path path(String s) { return Paths.get(s); }

    @Override
    public void apply() {
        Map<String,Object> model = new HashMap<>();
        model.put("namespace", "com.hydra.den");

        try {

            new BaseGenerator(source, target, config)
                .createDir(target)
                .createDirs(
                    to("src/main/java/webdrop/"),
                    to("src/test/java/webdrop/"),
                    to("src/main/resources/"),
                    to("src/main/resources/css/"),
                    to("src/main/resources/js/"),
                    to("src/main/resources/pages/"))
                .process(
                    model,
                    path("src/main/java/webdrop/App.ftl.java"),
                    to("src/main/java/webdrop/App.java"))
                .translate(
                    model,
                    path("src/main/java/webdrop/"),
                    "HelloWorldApplication.ftl.java",
                    "HelloWorldConfiguration.ftl.java",
                    "HelloWorldResource.ftl.java",
                    "Main.ftl.java",
                    "Saying.ftl.java",
                    "TemplateHealthCheck.ftl.java")
                .process(
                    model,
                    path("pom.ftl.xml"),
                    to("pom.xml"))
                .process(
                    model,
                    path("src/test/java/webdrop/AppTest.ftl.java"),
                    to("src/test/java/webdrop/AppTest.java"))
                .copy("src/main/resources/css/base.css")
                .copy("src/main/resources/js/some-stuff.js")
                .copy("src/main/resources/pages/index.html")
                .copy("app-nginx.conf")
                .copy("hello-world.yml")
                .copy("startup.sh")
                .apply();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
