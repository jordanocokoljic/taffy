package software.nofrills.taffy.core;

public class StepApplyException extends RuntimeException {
    public StepApplyException(Class<?> step, String message) {
        super(String.format("%s: %s", step, message));
    }
}
