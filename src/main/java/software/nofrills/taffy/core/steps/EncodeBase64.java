package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Base64Charset;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

public class EncodeBase64 implements Step {
    private final Base64Charset charset;

    public EncodeBase64(Base64Charset charset) {
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
