package software.nofrills.taffy.core.steps;

import org.junit.jupiter.api.Test;
import software.nofrills.taffy.core.Context;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomBytesTests {
    @Test
    public void applyGeneratesUserSpecifiedNumberOfBytes() {
        Context context = new Context(null, null);
        int numBytes = 32;

        RandomBytes randomBytes = new RandomBytes(numBytes);
        randomBytes.apply(context);

        assertEquals(numBytes, context.pop().length);
    }
}
