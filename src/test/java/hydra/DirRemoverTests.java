package hydra;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class DirRemoverTests extends FileHelpers {

    private Path target = Paths.get("files/targets/t3");
    private Path source = Paths.get("files/sources/s3");

    @Before
    public void setup() {
        createFile(target);
        new DirCopier(source, target).apply();
    }

    @Test
    public void should_delete_all_files_in_a_directory() throws IOException {
        assertThat(hasFiles(target), is(true));
        new DirRemover(target).apply();
        assertThat(target.toFile().exists(), is(false));
    }
}
