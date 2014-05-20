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


public class FileCopierTests {

    private static final Path S1 = Paths.get("files/sources/s1/first-file.txt");
    private static final Path T1 = Paths.get("files/targets/t1/first-file.txt");

    @Before
    public void setup() {
        if (T1.toFile().exists()) { T1.toFile().delete(); }
    }

    @After
    public void teardown() {
        if (T1.toFile().exists()) { T1.toFile().delete(); }
    }

    @Test
    public void should_find_src_file() throws IOException {
        FileCopier cp = new FileCopier(S1, T1);
        assertThat(cp.hasSource(), is(true));
    }

    @Test
    public void should_find_cp_of_src_file_in_target_dir() throws IOException {
        assertThat(T1.toFile().exists(), is(false));

        FileCopier cp = new FileCopier(S1, T1);
        cp.apply();

        assertThat(T1.toFile().exists(), is(true));
    }

    @Test
    public void should_find_copied_file_has_same_contents() throws IOException {
        FileCopier cp = new FileCopier(S1, T1);
        cp.apply();

        String a = Files.toString(S1.toFile(), Charsets.UTF_8);
        String b = Files.toString(T1.toFile(), Charsets.UTF_8);

        assertThat(a, is(b));
    }
}
