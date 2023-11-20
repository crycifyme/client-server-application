package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.util.Scanner;
import java.io.IOException;
import java.net.InetSocketAddress;

class Calcul {
    int a;
    int b;
    float rezult = 0;

    public float adunare(int a, int b) {
        this.a = a;
        this.b = b;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        rezult = a + b;
        return rezult;
    }

    public float scadere(int a, int b) {
        this.a = a;
        this.b = b;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        rezult = a - b;
        return rezult;
    }

    public float inmultire(int a, int b) {
        this.a = a;
        this.b = b;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        rezult = a * b;
        return rezult;
    }

    public float impartire(int a, int b) {
        this.a = a;
        this.b = b;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        rezult = a / b;
        return rezult;
    }
}

public class Main extends Calcul {

    public static void main(String[] args) throws IOException {
        Calcul c = new Calcul();

        var server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/add", exchange -> {
            System.out.println("/add handler");

            Scanner scanner = new Scanner(exchange.getRequestBody());
            String requestBody = scanner.nextLine();
            scanner.close();

            String[] numbers = requestBody.split(",");
            if (numbers.length == 2) {
                try {

                    int num1 = Integer.parseInt(numbers[0]);
                    int num2 = Integer.parseInt(numbers[1]);
                    int rez = (int) c.adunare(num1, num2);

                    ok(exchange, "Suma: " + rez + "\n");
                } catch (NumberFormatException e) {
                    notFound(exchange, "Body nu contine numere");
                }
            }
        });

        server.createContext("/subtract", exchange -> {
            System.out.println("/subtract handler");

            Scanner scanner = new Scanner(exchange.getRequestBody());
            String requestBody = scanner.nextLine();
            scanner.close();

            String[] numbers = requestBody.split(",");
            if (numbers.length == 2) {
                try {

                    int num1 = Integer.parseInt(numbers[0]);
                    int num2 = Integer.parseInt(numbers[1]);
                    int rez = (int) c.scadere(num1, num2);

                    ok(exchange, "Diferenta: " + rez + "\n");
                } catch (NumberFormatException e) {
                    notFound(exchange, "Body nu contine numere");
                }
            }
        });

        server.createContext("/multiply", exchange -> {
            System.out.println("/multiply handler");

            Scanner scanner = new Scanner(exchange.getRequestBody());
            String requestBody = scanner.nextLine();
            scanner.close();

            String[] numbers = requestBody.split(",");
            if (numbers.length == 2) {
                try {

                    int num1 = Integer.parseInt(numbers[0]);
                    int num2 = Integer.parseInt(numbers[1]);
                    int rez = (int) c.inmultire(num1, num2);

                    ok(exchange, "Produsul: " + rez + "\n");
                } catch (NumberFormatException e) {
                    notFound(exchange, "Body nu contine numere");

                }
            }
        });

        server.createContext("/divide", exchange -> {
            System.out.println("/divide handler");

            Scanner scanner = new Scanner(exchange.getRequestBody());
            String requestBody = scanner.nextLine();
            scanner.close();

            String[] numbers = requestBody.split(",");
            if (numbers.length == 2) {
                try {

                    int num1 = Integer.parseInt(numbers[0]);
                    int num2 = Integer.parseInt(numbers[1]);
                    int rez = (int) c.impartire(num1, num2);

                    ok(exchange, "Catul: " + rez + "\n");
                } catch (NumberFormatException e) {
                    notFound(exchange, "Body nu contine numere");
                }
            }
        });

        server.start();
    }


    private static void notFound(HttpExchange exchange, String responseText) throws IOException {
        sendRequest(exchange, 400, responseText);
    }

    private static void ok(HttpExchange exchange, String responseText) throws IOException {
        sendRequest(exchange, 200, responseText);
    }

    private static void sendRequest(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        exchange.sendResponseHeaders(statusCode, responseText.getBytes().length);

        try (var output = exchange.getResponseBody()) {
            output.write(responseText.getBytes());
        }
    }
}