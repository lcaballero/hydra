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

public class CreateTempDirTest extends FileHelpersForTesting {

    private Path target = Paths.get("files/targets/t8/");

    @Before public void setup() { createDir(target); }
    @After public void teardown() { delete(target); }

    @Test
    public void should_create_temp_dir() throws IOException {
        Path temp = new CreateTempDir(target, "hydra-den_").apply();
        assertThat(Files.exists(temp), is(true));
    }

}
