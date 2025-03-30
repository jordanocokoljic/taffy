package software.nofrills.taffy.core.encode;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

public class Base64 implements Step {
    public enum Charset {
        @JsonProperty("std-padded") STD_PADDED,
        @JsonProperty("std-raw") STD_RAW,
        @JsonProperty("url-padded") URL_PADDED,
        @JsonProperty("url-raw") URL_RAW,
    }

    private final Charset charset;

    public Base64(@JsonProperty("charset") Charset charset) {
        this.charset = charset;
    }

    private java.util.Base64.Encoder encoder() {
        return switch (charset) {
            case STD_PADDED -> java.util.Base64.getEncoder();
            case STD_RAW -> java.util.Base64.getEncoder().withoutPadding();
            case URL_PADDED -> java.util.Base64.getUrlEncoder();
            case URL_RAW -> java.util.Base64.getUrlEncoder().withoutPadding();
        };
    }

    @Override
    public void apply(Context context) {
        context.push(encoder().encode(context.pop()));
    }
}
