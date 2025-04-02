package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CtxTests {
    @Test
    public void duplicateDuplicatesStackHead() {
        String message = "Hello";
        Context context = new Context(null, null);
        ContextHelper.pushUTF8(context, message);

        CtxDuplicate duplicate = new CtxDuplicate();
        duplicate.apply(context);

        assertEquals(message, ContextHelper.popUTF8(context));
        assertEquals(message, ContextHelper.popUTF8(context));
    }
}
