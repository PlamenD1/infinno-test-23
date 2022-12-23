package org.example.BodyHandlers;

import org.example.BodyHandlers.BodySubscribers.StringBodySubscriber;
import org.example.HttpResponse;

import java.lang.reflect.Array;
import java.util.Arrays;

public class StringBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    public StringBodyHandler() {};
    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        System.out.println(Arrays.toString(responseInfo.body));
        return new StringBodySubscriber<>(responseInfo.body);
    }
}
