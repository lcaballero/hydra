package hydra;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipExtractor implements IApplier {

    public static final String DEFAULT_TEMPLATE_DIR = "template/";
    private Path tmp;
    private File jar;
    private String templateDir;

    public ZipExtractor(Path temp) {
        this(temp, null, DEFAULT_TEMPLATE_DIR);
    }

    public ZipExtractor(Path tmp, File jar, String templateDir) {
        this.tmp = tmp;
        this.jar = jar == null
            ? new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
            : jar;
        this.templateDir = templateDir == null || "".equals(templateDir)
            ? DEFAULT_TEMPLATE_DIR
            : templateDir;
    }

    public void apply() {
        System.out.println("Jar file: " + jar);
        System.out.println("Extracting: " + jar);
        extract(tmp, jar);
    }

    private void extract(Path temp, File jar) {

        ZipInputStream ins = null;

        try {

            ins = new ZipInputStream(new FileInputStream(jar));

            ZipEntry entry = ins.getNextEntry();
            byte[] buff = new byte[1024];
            int n = 0;

            while (entry != null) {
                String name = entry.getName();
                Path next = temp.resolve(name);

                if (!entry.getName().startsWith(this.templateDir)) {
                    entry = ins.getNextEntry();
                    continue;
                }

                if (entry.isDirectory() && !Files.exists(next)) {
                    System.out.printf("Creating: %s\n", next);
                    Files.createDirectory(next);
                    entry = ins.getNextEntry();
                    continue;
                }

                // Case where the entry isn't reported as a directory.
                if (Files.isDirectory(next)) {
                    System.out.printf("Creating: %s\n", next);
                    entry = ins.getNextEntry();
                    continue;
                }

                // Case: where the directories weren't first and therefor not created
                // before files in those directories were traversed, so here we
                // create the parent directories if they don't exist
                if (!Files.exists(next.getParent())) {
                    Files.createDirectories(next.getParent());
                }

                System.out.printf("Creating: %s\n", next);

                ByteArrayOutputStream outs = new ByteArrayOutputStream();

                while ((n = ins.read(buff, 0, buff.length)) > -1) {
                    outs.write(buff, 0, n);
                }

                Files.write(next, outs.toByteArray());

                entry = ins.getNextEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
