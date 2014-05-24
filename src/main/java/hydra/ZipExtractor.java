package hydra;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipExtractor implements IApplier {

    private Path tmp;
    private File jar;

    public ZipExtractor(Path temp) {
        this(temp, null);
    }

    public ZipExtractor(Path tmp, File jar) {
        this.tmp = tmp;
        this.jar = jar == null
            ? new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
            : jar;
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

                if (!entry.getName().startsWith("template/")) {
                    entry = ins.getNextEntry();
                    continue;
                }

                if (entry.isDirectory() && !Files.exists(next)) {
                    System.out.printf("Creating: %s\n", next);
                    Files.createDirectory(next);
                    entry = ins.getNextEntry();
                    continue;
                }

                // Case where the entry isn't reporting entry as a directory.
                if (Files.isDirectory(next)) {
                    System.out.printf("Creating: %s\n", next);
                    entry = ins.getNextEntry();
                    continue;
                }

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
