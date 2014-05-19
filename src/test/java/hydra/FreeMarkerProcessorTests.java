package hydra;

import freemarker.template.Configuration;
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
import static org.junit.Assert.assertThat;

public class FreeMarkerProcessorTests {

    private File ftlHome = Paths.get("files/templates/ftl/t1.ftl").toFile();
    private Map<String,Object> model = new HashMap<String, Object>();
    private FreeMarkerProcessor processor = null;

    @Before
    public void setup() throws IOException {
        model = new HashMap<String,Object>();
        model.put("name", "Hello, World!");

        processor = new FreeMarkerProcessor(new DefaultConfiguration(ftlHome.getParentFile()));
    }

    @Test
    public void should_produce_result_with_hw() throws IOException, TemplateException {
        String result = processor.apply("t1.ftl", model);
        assertThat(result, containsString("Hello, World!"));
    }


    @Test
    public void should_read_and_apply_json() throws IOException, TemplateException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(Paths.get("files/json/user.json").toFile(), User.class);

        String result = processor.apply("user.ftl", user);

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
        String result = processor.apply("user.ftl", user);

        assertThat(result, containsString(p.map("name").get("first").toString()));
        assertThat(result, containsString(p.map("name").get("last").toString()));
        assertThat(result, containsString(p.get("gender").toString()));
    }

}
