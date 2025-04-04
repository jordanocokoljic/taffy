package software.nofrills.taffy.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.steps.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MixinsTests {
    @ParameterizedTest(name = "{0}")
    @MethodSource("typeTests")
    public void correctTypesAreProduced(Object expected, String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Mixins.applyTo(mapper);

        Step step = mapper.readValue(json, Step.class);

        assertEquals(expected, step.getClass());
    }

    private static Stream<Arguments> typeTests() {
        return Stream.of(
            Arguments.of(
                InText.class,
                """
                {
                    "do": "in:text",
                    "text": "Text"
                }
                """
            ),
            Arguments.of(
                InStdin.class,
                """
                {
                    "do": "in:stdin",
                    "prompt": "Enter value"
                }
                """
            ),
            Arguments.of(
                OutStdout.class,
                """
                {
                    "do": "out:stdout"
                }
                """
            ),
            Arguments.of(
                EncodeBase64.class,
                """
                {
                    "do": "encode:base64",
                    "charset": "std-raw"
                }
                """
            ),
            Arguments.of(
                DecodeBase64.class,
                """
                {
                    "do": "decode:base64",
                    "charset": "std-raw"
                }
                """
            ),
            Arguments.of(
                EncodeHex.class,
                """
                {
                    "do": "encode:hex"
                }
                """
            ),
            Arguments.of(
                DecodeHex.class,
                """
                {
                    "do": "decode:hex"
                }
                """
            ),
            Arguments.of(
                RandomBytes.class,
                """
                {
                    "do": "rand:bytes",
                    "num": 32
                }
                """
            ),
            Arguments.of(
                HmacSHA256.class,
                """
                {
                    "do": "hmac:sha256",
                    "key": "1bcf8d9af805a3e331a44853308f917d079bfb5fcb32313724c0ba1a6e5780f5"
                }
                """
            ),
            Arguments.of(
                CtxDuplicate.class,
                """
                {
                    "do": "ctx:duplicate"
                }
                """
            )
        );
    }
}
