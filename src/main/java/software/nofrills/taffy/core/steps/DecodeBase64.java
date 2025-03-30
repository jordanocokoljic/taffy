package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Base64Charset;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.util.Base64;

public class DecodeBase64 implements Step {
    private final Base64Charset charset;

    public DecodeBase64(Base64Charset charset) {
        this.charset = charset;
    }

    private Base64.Decoder decoder() {
        return switch (charset) {
            case STD_PADDED, STD_RAW -> java.util.Base64.getDecoder();
            case URL_PADDED, URL_RAW -> java.util.Base64.getUrlDecoder();
        };
    }

    @Override
    public void apply(Context context) {
        context.push(decoder().decode(context.pop()));
    }
}
