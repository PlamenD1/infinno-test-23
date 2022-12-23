package org.example.BodyPublishers;

import org.example.HttpRequest;

public class StringBodyPublisher implements HttpRequest.BodyPublisher {
    String s;
    public StringBodyPublisher(String s) {
        this.s = s;
    }

    @Override
    public byte[] processBody() {
        return s.getBytes();
    }
}
