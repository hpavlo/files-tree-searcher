package org.example;

import java.io.File;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;

public class FilesTreeSearcher {
    private final BlockingQueue<Runnable> queue;
    private final Stack<File> stack = new Stack<>();

    public FilesTreeSearcher(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void search(String rootPath, int depth, String mask) {
        stack.push(new File(rootPath));
        while (!stack.isEmpty()) {
            File current = stack.pop();
            int currentDepth = getDepth(new File(rootPath), current);
            if (current.isDirectory() && currentDepth < depth) {
                fileProcessing(current, mask);
            }
        }
        queue.add(() -> System.exit(0));
    }

    private void fileProcessing(File currentFile, String mask) {
        File[] files = currentFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    stack.push(file);
                } else if (file.getName().contains(mask)) {
                    queue.add(() -> System.out.println(file.getAbsolutePath()));
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
