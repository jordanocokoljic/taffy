package software.nofrills.taffy.cli;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import software.nofrills.taffy.core.Base64Charset;
import software.nofrills.taffy.core.steps.EncodeBase64;
import software.nofrills.taffy.core.steps.InText;
import software.nofrills.taffy.core.steps.OutStdout;
import software.nofrills.taffy.core.Step;

import java.io.IOException;

final class Mixins {
    public static void applyTo(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Base64Charset.class, new Base64CharsetDeserializer());
        mapper.registerModule(module);

        mapper.addMixIn(Step.class, StepMixin.class);
        mapper.addMixIn(InText.class, InTextMixin.class);
        mapper.addMixIn(EncodeBase64.class, EncodeBase64Mixin.class);
    }
}

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "do"
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "in:text", value = InText.class),
        @JsonSubTypes.Type(name = "out:stdout", value = OutStdout.class),
        @JsonSubTypes.Type(name = "encode:base64", value = EncodeBase64.class)
})
interface StepMixin {
}

class Base64CharsetDeserializer extends JsonDeserializer<Base64Charset> {
    @Override
    public Base64Charset deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String charset = p.getText();

        return switch (charset) {
            case "std-padded" -> Base64Charset.STD_PADDED;
            case "std-raw" -> Base64Charset.STD_RAW;
            case "url-padded" -> Base64Charset.URL_PADDED;
            case "url-raw" -> Base64Charset.URL_RAW;
            default -> throw JsonMappingException.from(p, "Invalid Base64Charset indicator: " + charset);
        };
    }
}

abstract class InTextMixin extends InText {
    public InTextMixin(@JsonProperty("text") String text) {
        super(text);
    }
}

abstract class EncodeBase64Mixin extends EncodeBase64 {
    public EncodeBase64Mixin(@JsonProperty("charset") Base64Charset charset) {
        super(charset);
    }
}

