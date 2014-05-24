package hydra;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class RenamerTests extends FileHelpersForTesting {

    private Path t5 = Paths.get("files/targets/t5");

    @Before public void setup() { createDir(t5); }
    @After public void teardown() { new DirRemover(t5).apply(); }

    @Test
    public void should_not_rename_no_op_txt() {
        Path source = Paths.get("files/sources/s5/no-op.txt");
        Path target = Paths.get("files/targets/t5/no-op.txt");

        copy(source, target);
        new Renamer(target).apply();

        assertThat(Files.exists(target), is(true));
    }

    @Test
    public void should_remove_ftl_in_name() {
        Path source = Paths.get("files/sources/s5/rename-and-transform.ftl.yml");
        Path target = Paths.get("files/targets/t5/rename-and-transform.ftl.yml");
        Path expected = Paths.get("files/targets/t5/rename-and-transform.yml");

        copy(source, target);

        new FtlRenamer(target).apply();

        assertThat(Files.exists(target), is(false));
        assertThat(Files.exists(expected), is(true));
    }

    @Test
    public void should_swap_name() {
        Path source = Paths.get("files/sources/s5/hello-world.yml");
        Path target = Paths.get("files/targets/t5/hello-world.yml");
        Path expected = Paths.get("files/targets/t5/my-app.yml");

        copy(source, target);

        new NewName(target, "my-app.yml").apply();

        assertThat(Files.exists(target), is(false));
        assertThat(Files.exists(expected), is(true));
    }
}
