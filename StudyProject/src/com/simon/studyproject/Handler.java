package com.simon.studyproject;



import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Handler extends Thread {
    private static int counter = 0;

    private Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (var input = this.socket.getInputStream(); var output = new PrintStream(socket.getOutputStream())) {
            var url = this.getRequestUrl(input);

            if (url.contains("toLog=")) {
                var message = url.substring(url.indexOf("=") + 1);
                if (message.length() > 80) {
                    output.println("too long message");
                    return;
                }
                var writer = new FileWriter("log.txt", true);
                var print_line = new PrintWriter(writer);
                var dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                var now = LocalDateTime.now();
                print_line.write(dtf.format(now) + " " + message + "\r\n");
                output.println("your message was written to the log");
                print_line.close();
                writer.close();
                return;
            }
            else if (url.contains("randValue=")) {
                var twoNumbers = url.substring(url.indexOf("=") + 1).split("_");
                var randInt = new Random().nextInt(Integer.parseInt(twoNumbers[1]) + 1 -Integer.parseInt(twoNumbers[0])) + Integer.parseInt(twoNumbers[0]);
                System.out.println(randInt);
                output.println("randValue = " + randInt);
            }
            else if (url.contains("counterValue=")) {
                var number = url.substring(url.indexOf("=") + 1);
                counter += Integer.parseInt(number);
                output.println("counterValue = " + counter);
                System.out.println(counter);
            }
            else {
                output.println("Incorrect request");
            }
        } catch(IOException e) {

        }
    }

    private String getRequestUrl(InputStream input) {
        var reader = new Scanner(input).useDelimiter("\r\n");
        var line = reader.next();
        return line.split(" ")[1];
    }
}
