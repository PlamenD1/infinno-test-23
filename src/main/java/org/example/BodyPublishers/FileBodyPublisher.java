package org.example.BodyPublishers;

import org.example.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public class FileBodyPublisher implements HttpRequest.BodyPublisher {
    File file;

    public FileBodyPublisher(Path path) {
        this.file = new File(path.toUri());
    }

    @Override
    public byte[] processBody() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (FileReader reader = new FileReader(file)) {
            int _byte = reader.read();
            while (_byte != -1) {
                out.write(reader.read());
                _byte = reader.read();
            }
        }

        return out.toByteArray();
    }
}
