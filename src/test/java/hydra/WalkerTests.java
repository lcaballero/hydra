package hydra;

import com.google.common.base.Preconditions;
import com.google.common.collect.TreeTraverser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class WalkerTests {

    private Path s3 = Paths.get("files/sources/s3");
    private Path t3 = Paths.get("files/targets/t3");

    @Before
    public void setup() {
    }

    public boolean hasFiles(Path p) {
        File f = t3.toFile();
        File[] files = f.listFiles();
        return files != null && files.length > 0;
    }

    @Test
    public void should_copy_all_files_from_one_directory_to_another() throws IOException {
        assertThat(hasFiles(t3), is(true));

        new DirCopier(s3).copyToTarget(t3);

        assertThat(hasFiles(t3), is(false));
    }

    @Test
    public void should_delete_all_files_in_a_directory() throws IOException {

        assertThat(t3.toFile().list().length, is(0));

        // Produces only files, that can be deleted, then ran through again to delete the directories.
//        Files.walk(t3)
//            .map((a) -> a.toFile().getAbsoluteFile())
//            .filter((a) -> a.isFile())
//            .forEach(System.out::println);
//
//        Files.walk(s3)
//            .map((a) -> a.toFile().getPath())
//            .forEach(System.out::println);

    }


}
