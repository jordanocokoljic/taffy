package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.nio.charset.StandardCharsets;

public class InText implements Step {
    private final String text;

    public InText(String text) {
        this.text = text;
    }

    @Override
    public void apply(Context context) {
        context.push(text.getBytes(StandardCharsets.UTF_8));
    }
}
