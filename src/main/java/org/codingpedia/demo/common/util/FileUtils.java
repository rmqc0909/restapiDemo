package org.codingpedia.demo.common.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiedan11 on 2016/12/2.
 */
public class FileUtils {
    public static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
        try {
            FileOutputStream output = openOutputStream(destination);
            try {
                IOUtils.copy(source, output);
                output.close();
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(source);
        }
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!(file.canWrite()))
                throw new IOException("File '" + file + "' cannot be written to");
        }
        else {
            File parent = file.getParentFile();
            if ((parent != null) &&
                    (!(parent.mkdirs())) && (!(parent.isDirectory()))) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }
        return new FileOutputStream(file, append);
    }
}
