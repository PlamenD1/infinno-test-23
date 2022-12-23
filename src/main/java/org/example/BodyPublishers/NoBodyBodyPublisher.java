package org.example.BodyPublishers;

import org.example.HttpRequest;

public class NoBodyBodyPublisher implements HttpRequest.BodyPublisher {
    public NoBodyBodyPublisher() {

    }

    @Override
    public byte[] processBody() throws Exception {
        return new byte[0];
    }
}
