package software.nofrills.taffy.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.io.FilenameUtils;
import software.nofrills.taffy.core.Context;
import software.nofrills.taffy.core.Step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: taffy <recipe>");
            System.exit(1);
        }

        Map<String, Step[]> recipes = new HashMap<>();
        ObjectMapper mapper = new YAMLMapper();

        Path localCookbook = Paths.get(".taffy");
        try (var paths = Files.walk(localCookbook)) {
            paths.skip(1).forEach(p -> {
                try {
                    Step[] recipe = mapper.readValue(p.toFile(), new TypeReference<>() {});
                    recipes.put(FilenameUtils.removeExtension(p.getFileName().toString()), recipe);
                } catch (IOException e) {
                    // todo(jordan): Provide a better message here
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            // todo(jordan): Provide a better message here
            throw new RuntimeException(e);
        }

        String runRecipe = args[0];
        Step[] matched = recipes.get(runRecipe);
        if (matched == null) {
            System.err.printf("no loaded recipe matched %s%n", runRecipe);
            System.exit(1);
        }

        Context context = new Context(System.out);
        for (var step : matched) {
            step.apply(context);
        }
    }
}
