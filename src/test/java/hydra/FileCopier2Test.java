package hydra;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FileCopier2Test extends FileHelpersForTesting {

    private static final Path s2 = Paths.get("files/sources/s2/dir1/");
    private static final Path t2 = Paths.get("files/targets/t2/dir1/");

    @Before
    public void setup() {
        delete(t2);
        createDir(t2.getParent());
    }

    @After
    public void teardown() {
        delete(t2);
        delete(t2.getParent());
    }

    @Test
    public void should_find_source_dir() throws IOException {
        FileCopier cp = new FileCopier(s2, t2);
        assertThat("isDirectory", cp.isDirectory(), is(true));
        assertThat(cp.hasSource(), is(true));
    }

    @Test
    public void should_find_cp_of_source_dir_in_target_dir() throws IOException {
        assertThat(Files.exists(t2), is(false));

        FileCopier dcp = new FileCopier(s2, t2);
        dcp.apply();

        assertThat(dcp.isDirectory(), is(true));
        assertThat(Files.exists(t2), is(true));
    }
}
