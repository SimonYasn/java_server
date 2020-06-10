package com.simon.studyproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;

    private String directory;

    public Server(int port) {
        this.port = port;
    }



    void start() {
        try (var server = new ServerSocket(this.port)) {
            while (true) {
                var socket = server.accept();
                var thread = new Handler(socket);
                thread.start();
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(8080).start();
        var client = new Socket("localhost", 8080);
        System.out.println(client.getInputStream());
    }
}