package org.example.BodyHandlers;

import org.example.HttpResponse;

import java.io.File;
import java.nio.file.Path;

public class FileBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    File file;

    public FileBodyHandler(Path path) {
        this.file = new File(path.toAbsolutePath().toUri());
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {

        return null;
    }
}
