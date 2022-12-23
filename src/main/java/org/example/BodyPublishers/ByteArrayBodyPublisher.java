package org.example.BodyPublishers;

import org.example.HttpRequest;

public class ByteArrayBodyPublisher implements HttpRequest.BodyPublisher {
    byte[] bytes;

    public ByteArrayBodyPublisher(byte[] bytes) {
        this.bytes = bytes;
    }


    @Override
    public byte[] processBody() {
        return bytes;
    }
}
