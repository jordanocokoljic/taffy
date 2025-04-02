package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.*;

import java.util.Base64;

public class DecodeBase64 implements Step {
    private final Base64Charset charset;

    public DecodeBase64(Base64Charset charset) {
        if (charset == null) {
            throw new StepConstructionException(this.getClass(), "charset cannot be null");
        }

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
        try {
            context.push(decoder().decode(context.pop()));
        } catch (IllegalArgumentException e) {
            throw new StepApplicationException(this.getClass(), String.format("unable to decode data: %s", e));
        }
    }
}
