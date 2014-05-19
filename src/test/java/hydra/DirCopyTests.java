package hydra;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DirCopyTests {

    private static final Path S1 = Paths.get("files/sources/s2/dir1/");
    private static final Path T1 = Paths.get("files/targets/t2/dir1/");

    @Before
    public void setup() {
        if (T1.toFile().exists()) { T1.toFile().delete(); }
    }

    @After
    public void teardown() {
        if (T1.toFile().exists()) { T1.toFile().delete(); }
    }


    @Test
    public void should_find_source_dir() throws IOException {
        FileCopier cp = new FileCopier(S1, T1);
        assertThat("isDirectory", cp.isDirectory(), is(true));
        assertThat(cp.hasSource(), is(true));
    }

    @Test
    public void should_find_cp_of_source_dir_in_target_dir() throws IOException {
        assertThat(T1.toFile().exists(), is(false));

        FileCopier dcp = new FileCopier(S1, T1);
        dcp.exec();

        assertThat(dcp.isDirectory(), is(true));
        assertThat(T1.toFile().exists(), is(true));
    }
}
