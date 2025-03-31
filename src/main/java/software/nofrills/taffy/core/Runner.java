package software.nofrills.taffy.core;

import java.io.InputStream;
import java.io.PrintStream;

public class Runner {
    private final Step[] steps;

    public Runner(Step[] steps) {
        this.steps = steps;
    }

    public boolean run(InputStream in, PrintStream out, PrintStream err) {
        Context context = new Context(in, out);

        for (var i = 0; i < steps.length; i++) {
            try {
                steps[i].apply(context);
            } catch (StepApplyException e) {
                err.printf("An error occurred during step %d: %s%n", i, e.getMessage());
                return false;
            }
        }

        return true;
    }
}
