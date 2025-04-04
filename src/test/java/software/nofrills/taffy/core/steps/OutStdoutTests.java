package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutStdoutTests {
    @Test
    public void applyWritesToContextOut() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Context context = new Context(null, out);

        String message = "Hello, world";
        context.push(message.getBytes(StandardCharsets.UTF_8));

        OutStdout outStdout = new OutStdout(null);
        outStdout.apply(context);

        assertEquals(message + System.lineSeparator(), bytes.toString());
    }

    @Test
    public void ifPromptIsGivenOutputIsPrefixed() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Context context = new Context(null, out);

        String message = "Hello, world";
        context.push(message.getBytes(StandardCharsets.UTF_8));

        String prompt = "Result: ";
        OutStdout outStdout = new OutStdout(prompt);
        outStdout.apply(context);

        assertEquals(prompt + message + System.lineSeparator(), bytes.toString());
    }
}
