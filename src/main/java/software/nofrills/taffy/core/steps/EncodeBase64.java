package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.StepConstructionException;

import java.util.Base64;

public class EncodeBase64 implements Step {
    private final Base64.Encoder encoder;

    public EncodeBase64(String charset) {
        if (charset == null) {
            throw new StepConstructionException(this.getClass(), "charset cannot be null");
        }

        this.encoder = switch (charset) {
            case "std-padded" -> Base64.getEncoder();
            case "std-raw" -> Base64.getEncoder().withoutPadding();
            case "url-padded" -> Base64.getUrlEncoder();
            case "url-raw" -> Base64.getUrlEncoder().withoutPadding();
            default -> throw new StepConstructionException(this.getClass(), String.format("charset is invalid: %s", charset));
        };
    }

    @Override
    public void apply(Context context) {
        context.push(encoder.encode(context.pop()));
    }
}
