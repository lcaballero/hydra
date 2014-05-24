package hydra;

import freemarker.template.TemplateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class ProjectTests extends FileHelpersForTesting {

    private Path src = Paths.get("files/sources/s9/WebDrop/");
    private Path target = Paths.get("files/targets/t9/WebDrop");
    private Map<String,Object> model = null;

    @Before
    public void setup() throws IOException {
        model = new HashMap<>();
        model.put("namespace", "com.hydra.den");

        new DirRemover(target.getParent()).apply();
        createDir(target.getParent());
        createDir(target);
    }

    @After
    public void teardown() {
//        new DirRemover(target.getParent()).apply();
    }

    @Test
    public void should_have_created_base_dirs() throws IOException, TemplateException {
        new Project().apply();

        exists(target);

        exists(
            target,
            "src/",
            "src/main/",
            "src/main/java/",
            "src/main/java/webdrop/",
            "src/main/resources/css/",
            "src/main/resources/js/",
            "src/main/resources/pages/",
            "src/test/java/webdrop/"
        );
    }
}
