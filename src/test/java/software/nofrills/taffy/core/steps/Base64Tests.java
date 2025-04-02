package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import software.nofrills.taffy.core.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Base64Tests {
    @ParameterizedTest(name = "{0}")
    @MethodSource("testCases")
    public void encodePushesCorrectValueToContext(String charset, String expected) {
        Context context = new Context(null, null);
        context.push(source());

        EncodeBase64 b64 = new EncodeBase64(charset);
        b64.apply(context);

        assertEquals(expected, ContextHelper.popUTF8(context));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("testCases")
    public void decodePushesCorrectValueToContext(String charset, String raw) {
        Context context = new Context(null, null);
        ContextHelper.pushUTF8(context, raw);

        DecodeBase64 b64 = new DecodeBase64(charset);
        b64.apply(context);

        assertArrayEquals(source(), context.pop());
    }

    @Test
    public void decodeThrowsCorrectlyTypedException() {
        Context context = new Context(null, null);
        ContextHelper.pushUTF8(context, "[]");

        DecodeBase64 b64 = new DecodeBase64("std-padded");

        assertThrows(StepApplicationException.class, () -> b64.apply(context));
    }

    @Test
    public void encodeThrowsCorrectErrorIfCharsetIsNull() {
        var e = assertThrows(StepConstructionException.class, () -> new EncodeBase64(null));
        assertEquals(EncodeBase64.class, e.getStep());
        assertTrue(e.getMessage().contains("charset cannot be null"));
    }

    @Test
    public void encodeThrowsCorrectErrorIfCharsetIsInvalid() {
        var e = assertThrows(StepConstructionException.class, () -> new EncodeBase64("not-real"));
        assertEquals(EncodeBase64.class, e.getStep());
        assertTrue(e.getMessage().contains("charset is invalid"));
    }

    @Test
    public void decodeThrowsCorrectErrorIfCharsetIsNull() {
        var e = assertThrows(StepConstructionException.class, () -> new DecodeBase64(null));
        assertEquals(DecodeBase64.class, e.getStep());
        assertTrue(e.getMessage().contains("charset cannot be null"));
    }

    @Test
    public void decodeThrowsCorrectErrorIfCharsetIsInvalid() {
        var e = assertThrows(StepConstructionException.class, () -> new DecodeBase64("not-real"));
        assertEquals(DecodeBase64.class, e.getStep());
        assertTrue(e.getMessage().contains("charset is invalid"));
    }

    private static byte[] source() {
        try {
            return Hex.decodeHex("6a5a6169b7adfb");
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of("std-padded", "alphabet+w=="),
            Arguments.of("std-raw", "alphabet+w"),
            Arguments.of("url-padded", "alphabet-w=="),
            Arguments.of("url-raw", "alphabet-w")
        );
    }
}
