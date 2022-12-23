package org.example.BodyHandlers.BodySubscribers;

import org.example.HttpResponse;

import java.io.File;
import java.io.FileWriter;

public class FileBodySubscriber<T> implements HttpResponse.BodySubscriber<T> {
    File file;
    byte[] bytes;

    public FileBodySubscriber(File file, byte[] bytes) {
        this.file = file;
        this.bytes = bytes;
    }

    @Override
    public T getBody() throws Exception {
        file.createNewFile();

        try (FileWriter writer = new FileWriter(file)) {
            for (byte b : bytes) {
                writer.write(b);
            }
        }

        return null;
    }
}
