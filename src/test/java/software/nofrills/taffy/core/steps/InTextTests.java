package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;
import software.nofrills.taffy.core.StepConstructionException;

import static org.junit.jupiter.api.Assertions.*;

public class InTextTests {
    @Test
    public void applyPushesToStack() {
        Context context = new Context(null, null);
        InText text = new InText("Hello, world");

        text.apply(context);

        assertEquals("Hello, world", ContextHelper.popUTF8(context));
    }

    @Test
    public void throwsCorrectErrorIfTextIsNull() {
        var e = assertThrows(StepConstructionException.class, () -> new InText(null));
        assertEquals(InText.class, e.getStep());
        assertTrue(e.getMessage().contains("text cannot be null"));
    }
}
