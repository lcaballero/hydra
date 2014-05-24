package hydra;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class DirCopierTests extends FileHelpersForTesting {

    private Path source = Paths.get("files/sources/s3");
    private Path target = Paths.get("files/targets/t3");

    @Before
    public void setup() {
        new DirRemover(target).apply();
        createFile(target);
    }

    @Test
    public void should_copy_all_files_from_one_directory_to_another() {
        assertThat(hasFiles(target), is(false));
        new DirCopier(source, target).apply();
        assertThat(hasFiles(target), is(true));
    }
}
