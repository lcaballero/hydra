package hydra;

import java.nio.file.Path;

public class Generator {

    /**
     * An instance of a Generator carries out the file and code generation,
     * running template translation, etc.
     *
     * @param to A directory to place all generated files.
     * @param config A file holding any system configuration data, like the
     *               location of generator repo, gitignore file store,
     *               license store, etc.
     * @param source The generator source url, one that names the generator
     *               seed and any additional url keys.  For example:
     *               dropwizzard/webapp/angular.  Using a url is simpler than
     *               using some kind of CLI options parser.
     */
    public Generator(Path to, Path config, String source) {

    }
}
