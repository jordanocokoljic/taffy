package software.nofrills.taffy.core;

public class StepApplicationException extends RuntimeException {
    private final Class<?> step;

    public StepApplicationException(Class<?> step, String message) {
        super(String.format("unable to apply %s: %s", step.getSimpleName(), message));
        this.step = step;
    }

    public Class<?> getStep() {
        return step;
    }
}
