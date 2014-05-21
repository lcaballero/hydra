package hydra;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface IApplier {
    public void apply() throws IOException, TemplateException;
}
