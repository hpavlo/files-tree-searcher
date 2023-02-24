package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class TelnetServer {
    private static String rootPath;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java -jar <file>.jar <rootPath> <port>");
            return;
        }

        rootPath = args[0];
        int port = Integer.parseInt(args[1]);
        ServerSocket serverSocket = new ServerSocket(port);
        var requestsQueue = startSearcherThread();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            UserThread userThread = new UserThread(clientSocket, requestsQueue, rootPath);
            new Thread(userThread).start();
        }
    }

    private static BlockingQueue<UserRequest> startSearcherThread() {
        FilesTreeSearcher filesTreeSearcher = new FilesTreeSearcher(rootPath);
        Thread thread = new Thread(filesTreeSearcher, "filesTreeSearcher");
        thread.setDaemon(true);
        thread.start();
        return filesTreeSearcher.getRequestsQueue();
    }
}
