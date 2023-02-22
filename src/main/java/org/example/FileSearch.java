package org.example;

import java.io.File;
import java.util.Stack;

public class FileSearch {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -jar <file>.jar <rootPath> <depth> <mask>");
            return;
        }

        String rootPath = args[0];
        int depth = Integer.parseInt(args[1]);
        String mask = args[2];

        Stack<File> stack = new Stack<>();
        stack.push(new File(rootPath));

        while (!stack.isEmpty()) {
            File current = stack.pop();
            int currentDepth = getDepth(new File(rootPath), current);
            if (current.isDirectory() && currentDepth < depth) {
                File[] files = current.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            stack.push(file);
                        } else {
                            if (file.getName().contains(mask)) {
                                System.out.println(file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }

    private static int getDepth(File root, File dir) {
        int depth = 0;
        while (!dir.equals(root)) {
            dir = dir.getParentFile();
            depth++;
        }
        return depth;
    }
}
