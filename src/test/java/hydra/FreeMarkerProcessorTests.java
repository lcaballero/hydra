package hydra;

import freemarker.template.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
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

    private Configuration config = null;
    private File ftlHome = Paths.get("files/templates/ftl/t1.ftl").toFile();
    private Map<String,Object> model = new HashMap<String, Object>();
    private FreeMarkerProcessor processor = null;

    @Before
    public void setup() throws IOException {
        config = new DefaultConfiguration(ftlHome.getParentFile());

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "Hello, World!");

        model = map;

        processor = new FreeMarkerProcessor(config);
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

}
