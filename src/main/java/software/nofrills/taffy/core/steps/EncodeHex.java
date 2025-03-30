package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.binary.Hex;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.nio.charset.StandardCharsets;

public class EncodeHex implements Step {
    @Override
    public void apply(Context context) {
        context.push(Hex.encodeHexString(context.pop()).getBytes(StandardCharsets.UTF_8));
    }
}
