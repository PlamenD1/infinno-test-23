package org.example;

import org.example.BodyPublishers.ByteArrayBodyPublisher;
import org.example.BodyPublishers.FileBodyPublisher;
import org.example.BodyPublishers.NoBodyBodyPublisher;
import org.example.BodyPublishers.StringBodyPublisher;

import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    static class Builder {
        HttpRequest request;

        Builder() {
            this.request = new HttpRequest();
        }
        Builder(URI uri) {
            this.request = new HttpRequest();
            this.request.uri = uri;
        }

        Builder(HttpRequest request) {
            this.request = request;
        }

        Builder GET() {
            this.request.method = "GET";
            return new Builder(request);
        }

        Builder POST(BodyPublisher bodyPublisher) {
            this.request.method = "POST";
            this.request.bodyPublisher = bodyPublisher;
            return new Builder(request);
        }

        Builder PUT(BodyPublisher bodyPublisher) {
            this.request.method = "PUT";
            this.request.bodyPublisher = bodyPublisher;
            return new Builder(request);
        }

        Builder DELETE() {
            this.request.method = "DELETE";
            return new Builder(request);
        }

        Builder uri(URI uri) {
            this.request.uri = uri;
            return new Builder(request);
        }

        Builder setHeader(String name, String value) {
            this.request.headers.put(name, value);
            return new Builder(request);
        }

        Builder header(String name, String value) {
            this.request.headers.put(name, value);
            return new Builder(request);
        }

        Builder headers(String... values) {
            int length = values.length / 2;
            for (int i = 0; i < length; i += 2) {
                this.request.headers.put(values[i], values[i + 1]);
            }

            return new Builder(request);
        }

        Builder method(String method, BodyPublisher bodyPublisher) {
            this.request.method = method;
            this.request.bodyPublisher = bodyPublisher;
            return new Builder(request);
        }

        HttpRequest build() {
            return this.request;
        }
    }

    public interface BodyPublisher {
        byte[] processBody() throws Exception;
    }

    static class BodyPublishers {
        public static BodyPublisher ofString(String s) {
            return new StringBodyPublisher(s);
        }

        public static BodyPublisher fromFile(Path path) {
            return new FileBodyPublisher(path);
        }

        public static BodyPublisher ofByteArray(byte[] bytes) {
            return new ByteArrayBodyPublisher(bytes);
        }

        public static BodyPublisher noBody() {
            return new NoBodyBodyPublisher();
        }
    }

    static Builder newBuilder() {
        return new Builder();
    }

    Builder newBuilder(URI uri) {
        return new Builder(uri);
    }

    String method = "GET";
    URI uri;
    Map<String, String> headers = new HashMap<>();
    BodyPublisher bodyPublisher = BodyPublishers.noBody();
}