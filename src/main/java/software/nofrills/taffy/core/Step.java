package software.nofrills.taffy.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "do"
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "in:text", value = software.nofrills.taffy.core.in.Text.class),
        @JsonSubTypes.Type(name = "out:stdout", value = software.nofrills.taffy.core.out.Stdout.class),
        @JsonSubTypes.Type(name = "encode:base64", value = software.nofrills.taffy.core.encode.Base64.class)
})
public interface Step {
    void apply(Context context);
}
