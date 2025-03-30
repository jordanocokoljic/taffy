package software.nofrills.taffy.core;

import java.io.PrintStream;
import java.util.Stack;

public class Context {
    private final Stack<byte[]> stack;
    private final PrintStream out;

    public Context(PrintStream out) {
        this.stack = new Stack<>();
        this.out = out;
    }

    public void push(byte[] in) {
        stack.add(in);
    }

    public byte[] pop() {
        return stack.pop();
    }

    public PrintStream out() {
        return out;
    }
}
