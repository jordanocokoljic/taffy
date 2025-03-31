package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InStdinTests {
    @Test
    public void applyPromptsUserAndPushesToStack() {
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputBytes);

        InputStream in = new ByteArrayInputStream("Hello, world".getBytes(StandardCharsets.UTF_8));

        Context context = new Context(in, out);
        InStdin inStdin = new InStdin("Prompt:");

        inStdin.apply(context);

        assertEquals("Prompt: ", outputBytes.toString(StandardCharsets.UTF_8));
        assertEquals("Hello, world", ContextHelper.popUTF8(context));
    }
}
