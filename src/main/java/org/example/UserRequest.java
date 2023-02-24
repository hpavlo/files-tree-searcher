package org.example;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

public class UserRequest {
    private final OutputStream outputStream;
    private final Stack<File> stack = new Stack<>();
    private boolean isUserActive = true;
    private int depth;
    private String mask;

    public UserRequest(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getDepth() {
        return depth;
    }

    public String getMask() {
        return mask;
    }

    public void setRequestData(String rootPath, int depth, String mask) {
        this.depth = depth;
        this.mask = mask;
        stack.push(new File(rootPath));
    }

    public boolean isUserActive() {
        return isUserActive;
    }

    public void closeRequest() {
        isUserActive = false;
    }

    public void addFolder(File file) {
        stack.push(file);
    }

    public File getFolder() {
        return stack.pop();
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public void printFile(File file) {
        try {
            if (isUserActive()) {
                outputStream.write((file.getAbsolutePath() + "\r\n").getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
