package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import software.nofrills.taffy.core.Base64Charset;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.ContextHelper;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncodeBase64Tests {
    @ParameterizedTest
    @MethodSource("applyTestCases")
    public void applyEncodesAndPushesToStack(Base64Charset charset, String expected) {
        Context context = new Context(null);
        context.push(testInput());

        EncodeBase64 b64 = new EncodeBase64(charset);
        b64.apply(context);

        Assertions.assertEquals(expected, ContextHelper.popUTF8(context));
    }

    private static byte[] testInput() {
        try {
            return Hex.decodeHex("6a5a6169b7adfb");
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Arguments> applyTestCases() {
        return Stream.of(
            Arguments.of(Base64Charset.STD_PADDED, "alphabet+w=="),
            Arguments.of(Base64Charset.STD_RAW, "alphabet+w"),
            Arguments.of(Base64Charset.URL_PADDED, "alphabet-w=="),
            Arguments.of(Base64Charset.URL_RAW, "alphabet-w")
        );
    }
}
