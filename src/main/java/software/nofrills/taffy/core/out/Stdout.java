package software.nofrills.taffy.core.out;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.nio.charset.StandardCharsets;

public class Stdout implements Step {
    @Override
    public void apply(Context context) {
        try (var out = context.out()) {
            out.println(new String(context.pop(), StandardCharsets.UTF_8));
        }
    }
}
