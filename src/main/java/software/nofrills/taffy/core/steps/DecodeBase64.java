package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.*;

import java.util.Base64;

public class DecodeBase64 implements Step {
    private final Base64.Decoder decoder;

    public DecodeBase64(String charset) {
        if (charset == null) {
            throw new StepConstructionException(this.getClass(), "charset cannot be null");
        }

        this.decoder = switch (charset) {
            case "std-padded", "std-raw" -> Base64.getDecoder();
            case "url-padded", "url-raw" -> Base64.getUrlDecoder();
            default -> throw new StepConstructionException(this.getClass(), String.format("unrecognized charset: %s", charset));
        };
    }

    @Override
    public void apply(Context context) {
        try {
            context.push(decoder.decode(context.pop()));
        } catch (IllegalArgumentException e) {
            throw new StepApplicationException(this.getClass(), String.format("unable to decode data: %s", e));
        }
    }
}
