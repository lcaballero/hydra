package hydra;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class Project {

    public void apply() {

        Path source = Paths.get("files/sources/s9/WebDrop/");
        Path target = Paths.get("files/targets/t9/WebDrop/");
        Map<String, Object> model = getModel();

        ProjectGen.<Map<String,Object>>of(source, target, model)
            .apply((p) ->
                p.createDirs(
                    "src/main/resources/css/",
                    "src/main/resources/js/",
                    "src/main/resources/pages/"
                ))
            .apply((p) ->
                p.in("src/main/java/")
                    .toNamespace("webdrop", model.get("namespace"))
                    .process(
                        "App.ftl.java",
                        "HelloWorldApplication.ftl.java",
                        "HelloWorldConfiguration.ftl.java",
                        "HelloWorldResource.ftl.java",
                        "Main.ftl.java",
                        "Saying.ftl.java",
                        "TemplateHealthCheck.ftl.java"
                    ))
            .apply((p) ->
                p.in("src/test/java/")
                    .toNamespace("webdrop", model.get("namespace"))
                    .process("AppTest.ftl.java"))
            .translate("pom.ftl.xml", "pom.xml")
            .copy("src/main/resources/css/base.css")
            .copy("src/main/resources/js/some-stuff.js")
            .copy("src/main/resources/pages/index.html")
            .copy("app-nginx.conf")
            .copy("hello-world.yml")
            .copy("startup.sh")
        .apply();
    }

    private Map<String, Object> getModel() {
        Map<String,Object> map = new HashMap<>();
        map.put("namespace", "com.hydra.den");
        return map;
    }
}
