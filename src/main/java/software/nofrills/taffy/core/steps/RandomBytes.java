package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.StepConstructionException;

import java.security.SecureRandom;

public class RandomBytes implements Step {
    private final int numBytes;

    public RandomBytes(int numBytes) {
        if (numBytes < 0) {
            throw new StepConstructionException(this.getClass(), "number of bytes to generate cannot be negative");
        }

        this.numBytes = numBytes;
    }

    @Override
    public void apply(Context context) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[numBytes];

        random.nextBytes(bytes);
        context.push(bytes);
    }
}
