package software.nofrills.taffy.core.in;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTests {
    @Test
    public void applyPushesToStack() {
        Context context = new Context(null);
        Text text = new Text("Hello, world");

        text.apply(context);

        assertEquals("Hello, world", ContextHelper.popUTF8(context));
    }
}
