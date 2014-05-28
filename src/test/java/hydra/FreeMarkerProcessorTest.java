package hydra;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FreeMarkerProcessorTest {

    private Path ftlHome = Paths.get("files/templates/ftl/t1.ftl");
    private Map<String,Object> model = new HashMap<String, Object>();

    @Before
    public void setup() throws IOException {
        model = new HashMap<>();
        model.put("name", "Hello, World!");
    }

    @Test
    public void should_produce_result_with_hw() throws IOException, TemplateException {
        Configuration config = new DefaultConfiguration(ftlHome.getParent());
        String result =
            new FreeMarkerProcessor(model, new File(ftlHome.toFile().getName()))
            .apply(config);
        assertThat(result, containsString("Hello, World!"));
    }

    @Test
    public void should_read_and_apply_json() throws IOException, TemplateException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(Paths.get("files/json/user.json").toFile(), User.class);

        Configuration config = new DefaultConfiguration(ftlHome.getParent());
        String result = new FreeMarkerProcessor(user, new File("user.ftl"))
            .apply(config);

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
        Configuration config = new DefaultConfiguration(ftlHome.getParent());
        String result = new FreeMarkerProcessor(user, new File("user.ftl"))
            .apply(config);

        assertThat(result, containsString(p.map("name").get("first").toString()));
        assertThat(result, containsString(p.map("name").get("last").toString()));
        assertThat(result, containsString(p.get("gender").toString()));
    }

    @Test
    public void should_read_original_java_ftl_file_and_change_namespace() throws IOException, TemplateException {
        Path javaFile = Paths.get("files/sources/s4/WebDrop/src/main/java/webdrop/App.ftl.java");
        String code = Files.toString(javaFile.toFile(), Charsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(
            Paths.get("files/json/config-1.json").toFile(),
            new TypeReference<HashMap<String,Object>>(){});

        Configuration config = new DefaultConfiguration(javaFile.getParent());
        String newCode = new FreeMarkerProcessor(map, new File("App.ftl.java"))
            .apply(config);

        assertThat(code, containsString("${namespace}"));
        assertThat(newCode, containsString("com.hydra.den"));
    }

    @Test
    public void should_process_file_with_expressions_not_provided_values_as_in_the_pom() throws IOException, TemplateException {
        Path pom = Paths.get("files/sources/s4/WebDrop/pom.ftl.xml");
        String code = Files.toString(pom.toFile(), Charsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(
            Paths.get("files/json/config-1.json").toFile(),
            new TypeReference<HashMap<String,Object>>(){});

        Configuration config = new DefaultConfiguration(pom.getParent());
        String newCode = new FreeMarkerProcessor(map, new File(pom.toFile().getName()))
            .apply(config);

        assertThat(code, containsString("${namespace}"));
        assertThat(newCode, containsString("com.hydra.den"));
        assertThat(newCode.contains("${r\"dropwizard.version\"}"), is(false));
    }

    @Test
    public void should_find_file_in_nested_dir_structure() throws IOException, TemplateException {
        Path p = Paths.get("files/templates/ftl/a/");
        Configuration config = new DefaultConfiguration(p);

        Map<String,Object> map = new HashMap<>();
        map.put("name", "Bruce Wayne");

        FreeMarkerProcessor processor = new FreeMarkerProcessor(
            map, new File("nested/directory/with/a/template.ftl"));
        String s = processor.apply(config);

        assertThat(s, containsString("Well here's the"));
        assertThat(s, containsString("Bruce Wayne"));
    }

    @Test
    public void should_use_accessors_to_fill_in_template() throws IOException, TemplateException {
        Path p = Paths.get("files/templates/model/");
        Configuration config = new DefaultConfiguration(p);

        // So the BIG NOTE here is that Project Params CANNOT be an inner-class
        ProjectParams model = new ProjectParams();
        model.setNamespace("com.coolkid");
        model.setName("NewTool");

        FreeMarkerProcessor processor = new FreeMarkerProcessor(model, new File("model.ftl"));
        String s = processor.apply(config);

        assertThat(s, containsString("com.coolkid"));
        assertThat(s, containsString("NewTool"));
    }

    @Test
    public void should_use_accessors_of_user_to_fill_in_template() throws IOException, TemplateException {
        Path p = Paths.get("files/templates/model/");
        Configuration config = new DefaultConfiguration(p);

        User.Name name = new User.Name();
        name.setFirst("Bruce");
        name.setLast("Wayne");

        User model = new User();
        model.setName(name);

        FreeMarkerProcessor processor = new FreeMarkerProcessor(model, new File("user.ftl"));
        String s = processor.apply(config);

        assertThat(s, containsString("Bruce"));
        assertThat(s, containsString("Wayne"));
    }
}
