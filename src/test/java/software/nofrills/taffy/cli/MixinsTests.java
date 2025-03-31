package software.nofrills.taffy.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import software.nofrills.taffy.core.Base64Charset;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.steps.*;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MixinsTests {
    @Test
    public void base64CharsetIsCorrectlyDetermined() throws JsonProcessingException {
        var subtests = Map.of(
                "std-padded", Base64Charset.STD_PADDED,
                "std-raw", Base64Charset.STD_RAW,
                "url-padded", Base64Charset.URL_PADDED,
                "url-raw", Base64Charset.URL_RAW
        );

        for (var subtest : subtests.entrySet()) {
            ObjectMapper mapper = new ObjectMapper();
            Mixins.applyTo(mapper);

            Base64Charset charset = mapper.readValue('"' + subtest.getKey() + '"', Base64Charset.class);
            assertEquals(subtest.getValue(), charset);
        }

        assertThrows(JsonMappingException.class, () -> {
            ObjectMapper mapper = new ObjectMapper();
            Mixins.applyTo(mapper);

            mapper.readValue("\"notacharset\"", Base64Charset.class);
        });
    }

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
            )
        );
    }
}
