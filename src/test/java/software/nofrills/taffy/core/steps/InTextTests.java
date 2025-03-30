package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InTextTests {
    @Test
    public void applyPushesToStack() {
        Context context = new Context(null);
        InText text = new InText("Hello, world");

        text.apply(context);

        assertEquals("Hello, world", ContextHelper.popUTF8(context));
    }
}
