package software.nofrills.taffy.core.steps;

import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

public class CtxDuplicate implements Step {
    @Override
    public void apply(Context context) {
        byte[] top = context.pop();
        context.push(top);
        context.push(top);
    }
}
