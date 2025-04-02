package software.nofrills.taffy.core;

public class StepConstructionException extends RuntimeException {
    private final Class<?> step;

    public StepConstructionException(Class<?> step, String message) {
        super(String.format("unable to prepare %s: %s", step.getSimpleName(), message));
        this.step = step;
    }

    public Class<?> getStep() {
        return step;
    }
}
