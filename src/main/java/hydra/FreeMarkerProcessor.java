package hydra;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;


public class FreeMarkerProcessor {

    private Configuration config = null;

    public FreeMarkerProcessor(Configuration cfg) {
        this.config = cfg;
    }

    public String apply(String ftl, Object model) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        Template tpl = config.getTemplate(ftl);
        tpl.process(model, result);
        return result.toString();
    }
}
