package software.nofrills.taffy.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextTests {
    @Test
    public void pushAndPopOperateAsExpected() {
        Context context = new Context(null);
        context.push("Hello".getBytes(StandardCharsets.UTF_8));
        context.push("World".getBytes(StandardCharsets.UTF_8));

        assertEquals("World", ContextHelper.popUTF8(context));
        assertEquals("Hello", ContextHelper.popUTF8(context));
    }
}
