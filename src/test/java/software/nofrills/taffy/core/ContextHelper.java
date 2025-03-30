package software.nofrills.taffy.core;

import java.nio.charset.StandardCharsets;

public final class ContextHelper {
    public static String popUTF8(Context context) {
        return new String(context.pop(), StandardCharsets.UTF_8);
    }

    public static void pushUTF8(Context context, String str) {
        context.push(str.getBytes(StandardCharsets.UTF_8));
    }
}
