package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            System.out.println("Usage: java -jar <file>.jar <rootPath> <depth> <mask>");
            return;
        }

        String rootPath = args[0];
        int depth = Integer.parseInt(args[1]);
        String mask = args[2];

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        FilesTreeSearcher searcher = new FilesTreeSearcher(queue);
        new Thread(() -> searcher.search(rootPath, depth, mask), "searcher").start();

        while (true) {
            queue.take().run();
        }
    }
}
