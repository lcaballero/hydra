package hydra;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;


public class FreeMarkerProcessor {

    private Object model = null;
    private File ftl = null;

    public FreeMarkerProcessor(Object model, File ftl) {
        this.model = model;
        this.ftl = ftl;
    }

    public String apply(Configuration config) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        Template tpl = config.getTemplate(ftl.toString());
        tpl.process(model, result);
        return result.toString();
    }
}
