package software.nofrills.taffy.core.steps;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;
import software.nofrills.taffy.core.StepConstructionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacSHA256 implements Step {
    private static final String algorithm = "HmacSHA256";
    private final Mac mac;

    public HmacSHA256(String key) {
        if (key == null) {
            throw new StepConstructionException(this.getClass(), "key cannot be null");
        }

        byte[] secretBytes;

        try {
            secretBytes = Hex.decodeHex(key);
        } catch (DecoderException e) {
            throw new StepConstructionException(this.getClass(), "could not decode key: " + e);
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretBytes, algorithm);

        try {
            this.mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // As far as I can tell, these conditions cannot happen because:
            //  - The algorithm "HmacSHA256" is a listed algorithm in the Java Security specification.
            //  - An InvalidKeyException is only returned if the provided key is null, which would have
            //    been caught earlier.
            //
            // But just in case.

            throw new StepConstructionException(this.getClass(), "unable to initialize MAC: " + e);
        }
    }

    @Override
    public void apply(Context context) {
        context.push(mac.doFinal(context.pop()));
    }
}
