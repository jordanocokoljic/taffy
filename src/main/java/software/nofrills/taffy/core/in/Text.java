package software.nofrills.taffy.core.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.nio.charset.StandardCharsets;

public class Text implements Step {
    private final String text;

    public Text(@JsonProperty("text") String text) {
        this.text = text;
    }

    @Override
    public void apply(Context context) {
        context.push(text.getBytes(StandardCharsets.UTF_8));
    }
}
