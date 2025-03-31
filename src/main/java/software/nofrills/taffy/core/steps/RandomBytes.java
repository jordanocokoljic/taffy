package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.security.SecureRandom;

public class RandomBytes implements Step {
    private final int numBytes;

    public RandomBytes(int numBytes) {
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
