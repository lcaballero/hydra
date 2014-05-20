package hydra;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FtlFilterTests {

    public static final String CURRENT_DIR = ".";
    public static final String PARENT_DIR = "..";
    public static final String APP_FTL_JAVA = "App.ftl.java";
    public static final String HELLO_WORLD_APPLICATION_FTL_JAVA = "HelloWorldApplication.ftl.java";
    public static final String HELLO_WORLD_CONFIGURATION_FTL_JAVA = "HelloWorldConfiguration.ftl.java";
    public static final String _FTL_JAVA = "_.ftl.java";
    public static final String APP_TEST_JAVA = "ApppTest.java";
    public static final String NO_DOT_FTL_JAVA = "no-dot-ftl.java";
    public static final String FTL_JAVA = "ftl.java";
    public static final String POM_FTL_XML = "pom.ftl.xml";


    public List<File> getFiles() {
        return asFiles(
            CURRENT_DIR,
            PARENT_DIR,
            APP_FTL_JAVA,
            HELLO_WORLD_APPLICATION_FTL_JAVA,
            HELLO_WORLD_CONFIGURATION_FTL_JAVA,
            _FTL_JAVA,
            APP_TEST_JAVA,
            NO_DOT_FTL_JAVA,
            FTL_JAVA,
            POM_FTL_XML);
    }

    public List<File> asFiles(String... names) {
        return Arrays.asList(names).stream().map((s) -> new File(s)).collect(Collectors.toList());
    }

    public List<File> filterFiles(List<File> files) {
        return files.stream().filter((f) -> new FtlFilter().accept(f)).collect(Collectors.toList());
    }

    @Test
    public void should_find_several_ftl_java_files_in_webdrop_template_source() {
        Set<File> a = Sets.newHashSet(filterFiles(getFiles()));
        Set<File> b = Sets.newHashSet(asFiles(
            APP_FTL_JAVA,
            HELLO_WORLD_APPLICATION_FTL_JAVA,
            HELLO_WORLD_CONFIGURATION_FTL_JAVA,
            _FTL_JAVA,
            POM_FTL_XML
        ));

        assertThat(a.size(), greaterThan(0));
        assertThat(Sets.intersection(a, b).size(), is(b.size()));
    }

    @Test
    public void should_not_contain_non_dot_ftl_dot_files() {
        Set<File> a = Sets.newHashSet(filterFiles(getFiles()));
        Set<File> b = Sets.newHashSet(asFiles(
            APP_TEST_JAVA,
            NO_DOT_FTL_JAVA,
            FTL_JAVA
        ));

        assertThat(a.size(), greaterThan(0));
        assertThat(Sets.intersection(a, b).isEmpty(), is(true));
    }

    @Test
    public void should_not_contain_dot_dirs_parent_and_current_dir() {
        Set<File> a = Sets.newHashSet(filterFiles(getFiles()));
        Set<File> b = Sets.newHashSet(asFiles(CURRENT_DIR, PARENT_DIR));

        assertThat(a.size(), greaterThan(0));
        assertThat(Sets.intersection(a, b).isEmpty(), is(true));
    }

    @Test
    public void should_find_ftl_xml_pom_file() {
        Set<File> a = Sets.newHashSet(filterFiles(getFiles()));
        Set<File> b = Sets.newHashSet(asFiles(
            POM_FTL_XML
        ));

        assertThat(a.size(), greaterThan(0));
        assertThat(Sets.intersection(a, b).size(), is(1));
    }

    @Test
    public void should_not_find_plain_java_file() {

        Set<File> a = Sets.newHashSet(filterFiles(getFiles()));
        Set<File> b = Sets.newHashSet(asFiles(
            APP_TEST_JAVA
        ));

        assertThat(a.size(), greaterThan(0));
        assertThat(Sets.intersection(a, b).isEmpty(), is(true));
    }
}
