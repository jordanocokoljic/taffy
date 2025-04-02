package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.StepApplicationException;

import java.nio.charset.StandardCharsets;

public class DecodeHex implements Step {
    @Override
    public void apply(Context context) {
        try {
            context.push(Hex.decodeHex(new String(context.pop(), StandardCharsets.UTF_8)));
        } catch (DecoderException e) {
            throw new StepApplicationException(this.getClass(), String.format("unable to decode data: %s", e.getMessage()));
        }
    }
}
