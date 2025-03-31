package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.nio.charset.StandardCharsets;

public class OutStdout implements Step {
    @Override
    public void apply(Context context) {
        String str = new String(context.pop(), StandardCharsets.UTF_8);
        context.out.println(str);
    }
}
