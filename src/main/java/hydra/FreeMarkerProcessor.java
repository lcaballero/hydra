package hydra;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;


public class FreeMarkerProcessor {

    private Configuration config = null;
    private Object model = null;
    private String ftl = null;

    public FreeMarkerProcessor(Configuration cfg, Object model, String ftl) {
        this.config = cfg;
        this.model = model;
        this.ftl = ftl;
    }

    public String apply() throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        Template tpl = config.getTemplate(ftl);
        tpl.process(model, result);
        return result.toString();
    }
}
