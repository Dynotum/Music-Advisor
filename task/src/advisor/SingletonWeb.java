package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SingletonWeb {
    private HttpResponse<String> response;
    private HttpRequest request;
    private HttpClient client = HttpClient.newBuilder().build();
    private static SingletonWeb webRq;
    private final String AMPERSAND = "&";

    public static SingletonWeb getInstance() {
        if (webRq == null) {
            webRq = new SingletonWeb();
        }
        return webRq;
    }

    private SingletonWeb() {
    }

    public String getToken(String access_api_server, String code, String cliend_id, String client_secret) throws IOException, InterruptedException {
        final String access_token;
//        System.out.println(cliend_id + "&" + client_secret + "&grant_type=authorization_code&" + code + "&redirect_uri=http://localhost:5656");
//       curl -H "Authorization: Basic ZjM...zE=" -d grant_type=authorization_code -d code=MQCbtKe...44KN -d redirect_uri=https%3A%2F%2Fwww.foo.com%2Fauth https://accounts.spotify.com/api/token
        request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(access_api_server))
                .POST(HttpRequest.BodyPublishers.ofString(cliend_id + "&" + client_secret + "&grant_type=authorization_code&" + code + "&redirect_uri=http://localhost:5656"))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        access_token = response.body();

        return access_token;
    }
}


