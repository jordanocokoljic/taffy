package software.nofrills.taffy.core;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Stack;

public class Context {
    private final Stack<byte[]> stack;
    public final InputStream in;
    public final PrintStream out;

    public Context(InputStream in, PrintStream out) {
        this.stack = new Stack<>();
        this.in = in;
        this.out = out;
    }

    public void push(byte[] in) {
        stack.add(in);
    }

    public byte[] pop() {
        return stack.pop();
    }
}
