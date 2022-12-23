package org.example.BodyHandlers.BodySubscribers;

import org.example.HttpResponse;

import java.nio.charset.StandardCharsets;

public class StringBodySubscriber<T> implements HttpResponse.BodySubscriber<T> {
    byte[] bytes;

    public StringBodySubscriber(byte[] bytes) {
        this.bytes = bytes;
    }
    @Override
    public T getBody() {
        return (T) new String(bytes, StandardCharsets.UTF_8);
    }

}
