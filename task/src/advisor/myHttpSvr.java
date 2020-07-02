package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class myHttpSvr {

    private HttpServer httpServer;
    private String query;


    public String getQuery() {
        return query;
    }

    public myHttpSvr() {
        try {
            httpServer = HttpServer.create();
            httpServer.bind(new InetSocketAddress(5656), 0);

            httpServer.createContext("/", exchange -> {
                final String webMessage;
                query = exchange.getRequestURI().getQuery();

                if (query != null && query.startsWith("code=")) {
                    webMessage = "Got the code. Return back to your program.";
                } else {
                    webMessage = "Not found authorization code. Try again."; //Authorization code not found. Try again
                }

                exchange.sendResponseHeaders(200, webMessage.length());
                exchange.getResponseBody().write(webMessage.getBytes());
                exchange.getResponseBody().close();
                exchange.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void webStart() {
        this.httpServer.start();
    }

    public void webStop(int i) {
        this.httpServer.stop(i);
    }
}