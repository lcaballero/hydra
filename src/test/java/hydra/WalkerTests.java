package hydra;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class WalkerTests {

    private Path s3 = Paths.get("files/sources/s3");
    private Path t3 = Paths.get("files/targets/t3");

    @Before
    public void setup() {

    }

    @Test
    public void should_copy_all_files_from_one_directory_to_another() throws IOException {

        assertThat(t3.toFile().list().length, is(0));

//        // Produces only files, that can be deleted, then ran through again to delete the directories.
//        Files.walk(t3)
//            .map((a) -> a.toFile().getAbsoluteFile())
//            .filter((a) -> a.isFile())
//            .forEach(System.out::println);
//
//        Files.walk(s3)
//            .map((a) -> a.toFile().getPath())
//            .forEach(System.out::println);
    }

    @Test
    public void should_delete_all_files_in_a_directory() throws IOException {

        assertThat(t3.toFile().list().length, is(0));

        // Produces only files, that can be deleted, then ran through again to delete the directories.
        Files.walk(t3)
            .map((a) -> a.toFile().getAbsoluteFile())
            .filter((a) -> a.isFile())
            .forEach(System.out::println);

        Files.walk(s3)
            .map((a) -> a.toFile().getPath())
            .forEach(System.out::println);

    }


}
