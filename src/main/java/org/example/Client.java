package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        try (var httpClient = HttpClient.newHttpClient()) {
            while (true) {
                var scanner = new Scanner(System.in);

                System.out.print("Introdu numerele despartite prin virgula ");
                var input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                var request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8081/add"))
                        .header("Content-Type", "text/plain")
                        .POST(HttpRequest.BodyPublishers.ofString(input))
                        .build();

                var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println(response.body());
                var request1 = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8081/subtract"))
                        .header("Content-Type", "text/plain")
                        .POST(HttpRequest.BodyPublishers.ofString(input))
                        .build();

                var response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
                System.out.println(response1.body());

                var request2 = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8081/multiply"))
                        .header("Content-Type", "text/plain")
                        .POST(HttpRequest.BodyPublishers.ofString(input))
                        .build();

                var response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
                System.out.println(response2.body());

                var request3 = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8081/divide"))
                        .header("Content-Type", "text/plain")
                        .POST(HttpRequest.BodyPublishers.ofString(input))
                        .build();

                var response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());
                System.out.println(response3.body());

            }
        }
    }
}
