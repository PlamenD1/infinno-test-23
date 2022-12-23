package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    enum Redirect {NEVER, ALWAYS, NORMAL};
    static class Builder {
        HttpClient client;

        Builder() {
            this.client = HttpClient.newHttpClient();
        }

        Builder(HttpClient client) {
            this.client = client;
        }

        Builder followRedirects(Redirect redirect) {
            client.redirectPolicy = redirect;
            return new Builder(client);
        }
    }

    Redirect redirectPolicy = Redirect.NORMAL;

    static HttpClient newHttpClient() {
        return new HttpClient();
    }

    static Builder newBuilder() {
        return new Builder();
    }

    public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> bodyHandler) throws RuntimeException {
        try (Socket socket = new Socket(request.uri.getHost(), 80)) {
            OutputStreamWriter out =  new OutputStreamWriter(socket.getOutputStream());

            out.write(request.method + " " + request.uri.getPath() + " HTTP/1.1\n");

            request.headers.put("Host", request.uri.getHost());
            for (var entry : request.headers.entrySet()) {
                out.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            out.write("\n");

            byte[] body = request.bodyPublisher.processBody();
            for (byte b : body) {
                out.write(b);
            }
            out.flush();

            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(isReader);
            String line = bufferedReader.readLine();
            String[] queryArgs = line.split(" ");

            int status = Integer.parseInt(queryArgs[1]);
            HttpResponse.ResponseInfo responseInfo = new HttpResponse.ResponseInfo();
            responseInfo.statusCode = status;

            Map<String, String> headers = new HashMap<>();

            line = bufferedReader.readLine();
            while (!line.equals("")) {
                String[] header = line.split(": ");
                headers.put(header[0], header[1]);
                line = bufferedReader.readLine();
            }

            int contentLength = Integer.parseInt(headers.get("Content-Length"));

            byte[] bytes = new byte[contentLength];

            for (int i = 0; i < contentLength; i++) {
                bytes[i] = (byte) bufferedReader.read();
            }

            responseInfo.body = bytes;
            HttpResponse<T> response = new HttpResponse<>();
            response.body = bodyHandler.apply(responseInfo).getBody();

            out.close();
            bufferedReader.close();

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
