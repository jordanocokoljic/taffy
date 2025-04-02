package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;
import software.nofrills.taffy.core.StepConstructionException;

import static org.junit.jupiter.api.Assertions.*;

public class HmacSHA256Tests {
    @Test
    public void applyPushesCorrectHmacToStack() {
        Context context = new Context(null, null);
        ContextHelper.pushUTF8(context, "Hello, world");

        HmacSHA256 hmacSHA256 = new HmacSHA256("1bcf8d9af805a3e331a44853308f917d079bfb5fcb32313724c0ba1a6e5780f5");
        hmacSHA256.apply(context);

        String expected = "a2812edc7c491688d752e5f301f7bdfb5ea6e914385637915f01ed4ae6805131";
        assertEquals(expected, Hex.encodeHexString(context.pop()));
    }

    @Test
    public void throwsExceptionIfKeyIsNull() {
        var e = assertThrows(StepConstructionException.class, () -> new HmacSHA256(null));
        assertEquals(HmacSHA256.class, e.getStep());
        assertTrue(e.getMessage().contains("key cannot be null"));
    }

    @Test
    public void throwsExceptionWithUndecodableKey() {
        Exception e = assertThrows(StepConstructionException.class, () -> new HmacSHA256("----"));
        assertTrue(e.getMessage().contains("could not decode key"));
    }
}
