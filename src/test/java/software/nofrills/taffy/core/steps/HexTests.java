package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;
import software.nofrills.taffy.core.StepApplicationException;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class HexTests {
    static final byte[] encoded = "48656c6c6f2c20776f726c64".getBytes(StandardCharsets.UTF_8);
    static final String plain = "Hello, world";

    @Test
    public void encodePushesCorrectValueToContext() {
        Context context = new Context(null, null);
        context.push(plain.getBytes(StandardCharsets.UTF_8));

        EncodeHex hex = new EncodeHex();
        hex.apply(context);

        assertArrayEquals(encoded, context.pop());
    }

    @Test
    public void decodePushesCorrectValueToContext() {
        Context context = new Context(null, null);
        context.push(encoded);

        DecodeHex hex = new DecodeHex();
        hex.apply(context);

        assertEquals(plain, ContextHelper.popUTF8(context));
    }

    @Test
    public void decodeThrowsCorrectlyTypedException() {
        Context context = new Context(null, null);
        ContextHelper.pushUTF8(context, "ZZZZ");

        DecodeHex hex = new DecodeHex();

        assertThrows(StepApplicationException.class, () -> hex.apply(context));
    }
}
