package org.example;

import org.example.BodyHandlers.FileBodyHandler;
import org.example.BodyHandlers.StringBodyHandler;

import java.nio.file.Path;
import java.util.Map;

public class HttpResponse<T> {
    static class BodyHandlers {
        public static BodyHandler<String> ofString() {
            return new StringBodyHandler<>();
        }

        public BodyHandler<Void> ofFile(Path path) {
            return new FileBodyHandler<>(path);
        }
    }

    public interface BodyHandler<T> {
        BodySubscriber<T> apply(ResponseInfo responseInfo);
    }

    public interface BodySubscriber<T> {
        T getBody() throws Exception;
    }

    public static class ResponseInfo {
        public int statusCode;
        public Map<String, String> headers;
        public byte[] body;
    }

    T body;

    T body() throws Exception {
        return body;
    }
}
