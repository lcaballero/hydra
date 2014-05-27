package hydra;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class FileCopier1Test extends FileHelpersForTesting {

    private static final Path s1 = Paths.get("files/sources/s1/first-file.txt");
    private static final Path t1 = Paths.get("files/targets/t1/first-file.txt");

    @Before
    public void setup() {
        createDir(t1.getParent());
        delete(t1);
    }

    @After
    public void teardown() {
        delete(t1);
        delete(t1.getParent());
    }

    @Test
    public void should_find_src_file() throws IOException {
        FileCopier cp = new FileCopier(s1, t1);
        assertThat(cp.hasSource(), is(true));
    }

    @Test
    public void should_find_cp_of_src_file_in_target_dir() throws IOException {
        assertThat(t1.toFile().exists(), is(false));

        FileCopier cp = new FileCopier(s1, t1);
        cp.apply();

        assertThat(t1.toFile().exists(), is(true));
    }

    @Test
    public void should_find_copied_file_has_same_contents() throws IOException {
        FileCopier cp = new FileCopier(s1, t1);
        cp.apply();

        String a = Files.toString(s1.toFile(), Charsets.UTF_8);
        String b = Files.toString(t1.toFile(), Charsets.UTF_8);

        assertThat(a, is(b));
    }
}
