package software.nofrills.taffy.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.io.FilenameUtils;
import software.nofrills.taffy.core.Runner;
import software.nofrills.taffy.core.Step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
        loadRecipesFromDirectory(Paths.get(".taffy"), "local", recipes);
        loadRecipesFromDirectory(getGlobalRecipePath(), "global", recipes);

        String runRecipe = args[0];
        Step[] matched = recipes.get("local:" + runRecipe);
        if (matched == null) {
            matched = recipes.get("global:" + runRecipe);
        }

        if (matched == null) {
            System.err.printf("no loaded recipe matched '%s'%n", runRecipe);
            System.exit(1);
        }

        Runner runner = new Runner(matched);
        boolean ok = runner.run(System.in, System.out, System.err);

        if (!ok) {
            System.exit(1);
        }
    }

    private static Path getGlobalRecipePath() {
        String os = System.getProperty("os.name").toLowerCase();

        String configDir;
        if (os.contains("win")) {
            configDir = System.getenv("APPDATA");
        } else {
            String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");
            configDir = xdgConfigHome != null ? xdgConfigHome : System.getenv("HOME");
        }

        if (configDir == null) {
            return null;
        }

        return Paths.get(configDir, ".taffy");
    }

    private static void loadRecipesFromDirectory(Path dir, final String namespace, Map<String, Step[]> recipes) {
        if (dir == null) {
            return;
        }

        ObjectMapper mapper = new YAMLMapper();
        Mixins.applyTo(mapper);

        try (var paths = Files.walk(dir)) {
            paths.skip(1).forEach(p -> {
                try {
                    Step[] recipe = mapper.readValue(p.toFile(), new TypeReference<>() {});
                    recipes.put(namespace + ":" + FilenameUtils.removeExtension(p.getFileName().toString()), recipe);
                } catch (InvalidTypeIdException e) {
                    System.err.printf("recipe (%s) contained unrecognized step: %s", p, e.getTypeId());
                    System.exit(1);
                } catch (IOException e) {
                    System.err.printf("a fatal error occurred when trying to load recipe %s: %s", p, e);
                    System.exit(1);
                }
            });
        } catch (NoSuchFileException ignored) {
        } catch (IOException e) {
            System.err.printf("a fatal error occurred when trying to load local recipes: %s", e);
            System.exit(1);
        }
    }
}
