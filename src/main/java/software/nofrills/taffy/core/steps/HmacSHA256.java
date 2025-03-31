package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.StepApplyException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacSHA256 implements Step {
    private static final String algorithm = "HmacSHA256";
    private final String key;

    public HmacSHA256(String key) {
        this.key = key;
    }

    @Override
    public void apply(Context context) {
        byte[] secretBytes;

        try {
            secretBytes = Hex.decodeHex(key);
        } catch (DecoderException e) {
            throw new StepApplyException(this.getClass(), "cannot decode key: " + e);
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretBytes, algorithm);

        Mac mac = getInitializedMac(secretKeySpec);
        context.push(mac.doFinal(context.pop()));
    }

    private static Mac getInitializedMac(SecretKeySpec secretKeySpec) {
        Mac mac = null;

        try {
            mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
            // As far as I can tell, neither of these could actually happen:
            //  - The algorithm "HmacSHA256" is a listed algorithm, and is hardcoded here.
            //  - InvalidKeyException only gets returned if the provided secret key is null,
            //    but this would have caused a failure much earlier.
        }

        return mac;
    }
}
