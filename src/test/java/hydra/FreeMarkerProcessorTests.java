package hydra;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import freemarker.template.TemplateException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FreeMarkerProcessorTests {

    private File ftlHome = Paths.get("files/templates/ftl/t1.ftl").toFile();
    private Map<String,Object> model = new HashMap<String, Object>();

    @Before
    public void setup() throws IOException {
        model = new HashMap<>();
        model.put("name", "Hello, World!");
    }

    @Test
    public void should_produce_result_with_hw() throws IOException, TemplateException {
        String result =
            new FreeMarkerProcessor(
                new DefaultConfiguration(ftlHome.getParentFile()),
                model,
                ftlHome.getName())
            .apply();
        assertThat(result, containsString("Hello, World!"));
    }

    @Test
    public void should_read_and_apply_json() throws IOException, TemplateException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(Paths.get("files/json/user.json").toFile(), User.class);

        String result = new FreeMarkerProcessor(
                new DefaultConfiguration(ftlHome.getParentFile()),
                user,
                "user.ftl")
            .apply();

        assertThat(result, containsString(user.getName().getFirst()));
        assertThat(result, containsString(user.getName().getLast()));
        assertThat(result, containsString(user.getGender().toString()));
    }

    @Test
    public void should_read_and_apply_json_map() throws IOException, TemplateException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> user = mapper.readValue(
            Paths.get("files/json/user.json").toFile(),
            new TypeReference<Map<String,Object>>() {});

        Pre p = new Pre(user);
        String result = new FreeMarkerProcessor(
                new DefaultConfiguration(ftlHome.getParentFile()),
                user,
                "user.ftl")
            .apply();

        assertThat(result, containsString(p.map("name").get("first").toString()));
        assertThat(result, containsString(p.map("name").get("last").toString()));
        assertThat(result, containsString(p.get("gender").toString()));
    }

    @Test
    public void should_read_original_java_ftl_file_and_change_namespace() throws IOException, TemplateException {
        File javaFile = Paths.get("files/sources/s4/WebDrop/src/main/java/webdrop/App.ftl.java").toFile();
        String code = Files.toString(javaFile, Charsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(
            Paths.get("files/json/config-1.json").toFile(),
            new TypeReference<HashMap<String,Object>>(){});

        String newCode = new FreeMarkerProcessor(
                new DefaultConfiguration(javaFile.getParentFile()),
                map,
                "App.ftl.java")
            .apply();

        assertThat(code, containsString("${namespace}"));
        assertThat(newCode, containsString("com.hydra.den"));
    }

    @Test
    public void should_process_file_with_expressions_not_provided_values_as_in_the_pom() throws IOException, TemplateException {
        File pom = Paths.get("files/sources/s4/WebDrop/pom.ftl.xml").toFile();
        String code = Files.toString(pom, Charsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(
            Paths.get("files/json/config-1.json").toFile(),
            new TypeReference<HashMap<String,Object>>(){});

        String newCode = new FreeMarkerProcessor(
                new DefaultConfiguration(pom.getParentFile()),
                map,
                pom.getName())
            .apply();

        assertThat(code, containsString("${namespace}"));
        assertThat(newCode, containsString("com.hydra.den"));
        assertThat(newCode.contains("${r\"dropwizard.version\"}"), is(false));
    }
}
