package org.example;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FilesTreeSearcher implements Runnable {
    private final BlockingQueue<UserRequest> requestsQueue = new LinkedBlockingQueue<>();
    private final File rootFolder;

    public FilesTreeSearcher(String rootPath) {
        rootFolder = new File(rootPath);
    }

    @Override
    public void run() {
        while (true) {
            try {
                UserRequest request = requestsQueue.take();
                File current = request.getFolder();
                int currentDepth = getDepth(rootFolder, current);
                if (current.isDirectory() && currentDepth < request.getDepth()) {
                    fileProcessing(current, request);
                }
                if (!request.isStackEmpty() && request.isUserActive()) {
                    requestsQueue.add(request);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public BlockingQueue<UserRequest> getRequestsQueue() {
        return requestsQueue;
    }

    private void fileProcessing(File currentFile, UserRequest request) {
        File[] files = currentFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    request.addFolder(file);
                } else if (file.getName().contains(request.getMask())) {
                    request.printFile(file);
                }
            }
        }
    }

    private int getDepth(File root, File dir) {
        int depth = 0;
        while (!dir.equals(root)) {
            dir = dir.getParentFile();
            depth++;
        }
        return depth;
    }
}
