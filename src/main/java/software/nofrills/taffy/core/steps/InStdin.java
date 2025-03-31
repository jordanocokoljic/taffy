package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.StepApplyException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class InStdin implements Step {
    private final String prompt;

    public InStdin(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public void apply(Context context) {
        context.out.printf("%s ", prompt);
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.in));
        String input;

        try {
            input = reader.readLine();
        } catch (IOException e) {
            throw new StepApplyException(this.getClass(), "unable to read user input");
        }

        context.push(input.getBytes(StandardCharsets.UTF_8));
    }
}
