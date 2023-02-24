package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class UserThread implements Runnable {
    private static final String EXIT_COMMAND = "exit";
    private final BlockingQueue<UserRequest> requestsQueue;
    private final Socket clientSocket;
    private final String rootPath;

    public UserThread(Socket clientSocket,
                      BlockingQueue<UserRequest> requestsQueue,
                      String rootPath) {
        this.clientSocket = clientSocket;
        this.requestsQueue = requestsQueue;
        this.rootPath = rootPath;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            UserRequest request = new UserRequest(outputStream);
            outputStream.write(("Welcome to Telnet Server!\r\n"
                    + "Please, enter your command in format: <depth> <mask>\r\n").getBytes());
            Scanner scanner = new Scanner(inputStream);

            while (request.isUserActive() && scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                commandProcessing(command, request, outputStream);
            }

            requestsQueue.remove(request);
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void commandProcessing(String command,
                                   UserRequest request,
                                   OutputStream outputStream)
            throws IOException {
        if (command.equals(EXIT_COMMAND)) {
            request.closeRequest();
        } else if (userCommandValidation(command)) {
            int firstSpaceIndex = command.indexOf(' ');
            int depth = Integer.parseInt(command.substring(0, firstSpaceIndex));
            String mask = command.substring(firstSpaceIndex + 1);
            request.setRequestData(rootPath, depth, mask);
            requestsQueue.add(request);
        } else {
            outputStream.write(("Please, enter a valid command"
                    + " in format: <depth> <mask>\r\n").getBytes());
        }
    }

    private boolean userCommandValidation(String command) {
        return command.matches("^\\d+\\s+[\\w\\s,.-]+$");
    }
}
