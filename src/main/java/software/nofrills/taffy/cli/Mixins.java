package software.nofrills.taffy.cli;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.steps.*;

final class Mixins {
    public static void applyTo(ObjectMapper mapper) {
        mapper.addMixIn(Step.class, StepMixin.class);
        mapper.addMixIn(InText.class, InTextMixin.class);
        mapper.addMixIn(InStdin.class, InStdinMixin.class);
        mapper.addMixIn(OutStdout.class, OutStdoutMixin.class);
        mapper.addMixIn(EncodeBase64.class, EncodeBase64Mixin.class);
        mapper.addMixIn(DecodeBase64.class, DecodeBase64Mixin.class);
        mapper.addMixIn(RandomBytes.class, RandomBytesMixin.class);
        mapper.addMixIn(HmacSHA256.class, HmacSHA256Mixin.class);
    }
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "do"
)
@JsonSubTypes({
    @JsonSubTypes.Type(name = "in:text", value = InText.class),
    @JsonSubTypes.Type(name = "in:stdin", value = InStdin.class),
    @JsonSubTypes.Type(name = "out:stdout", value = OutStdout.class),
    @JsonSubTypes.Type(name = "encode:base64", value = EncodeBase64.class),
    @JsonSubTypes.Type(name = "encode:hex", value = EncodeHex.class),
    @JsonSubTypes.Type(name = "decode:base64", value = DecodeBase64.class),
    @JsonSubTypes.Type(name = "decode:hex", value = DecodeHex.class),
    @JsonSubTypes.Type(name = "rand:bytes", value = RandomBytes.class),
    @JsonSubTypes.Type(name = "hmac:sha256", value = HmacSHA256.class),
    @JsonSubTypes.Type(name = "ctx:duplicate", value = CtxDuplicate.class)
})
interface StepMixin {
}

abstract class InTextMixin extends InText {
    public InTextMixin(@JsonProperty("text") String text) {
        super(text);
    }
}

abstract class InStdinMixin extends InStdin {
    public InStdinMixin(@JsonProperty("prompt") String prompt) {
        super(prompt);
    }
}

abstract class OutStdoutMixin extends OutStdout {
    public OutStdoutMixin(@JsonProperty("prompt") String prompt) {
        super(prompt);
    }
}

abstract class EncodeBase64Mixin extends EncodeBase64 {
    public EncodeBase64Mixin(@JsonProperty("charset") String charset) {
        super(charset);
    }
}

abstract class DecodeBase64Mixin extends DecodeBase64 {
    public DecodeBase64Mixin(@JsonProperty("charset") String charset) {
        super(charset);
    }
}

abstract class RandomBytesMixin extends RandomBytes {
    public RandomBytesMixin(@JsonProperty("num") int numBytes) {
        super(numBytes);
    }
}

abstract class HmacSHA256Mixin extends HmacSHA256 {
    public HmacSHA256Mixin(@JsonProperty("key") String key) {
        super(key);
    }
}