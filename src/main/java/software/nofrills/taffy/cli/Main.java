package software.nofrills.taffy.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.io.FilenameUtils;
import software.nofrills.taffy.core.Runner;
import software.nofrills.taffy.core.Step;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: taffy <recipe>");
            System.exit(1);
        }

        try (
            var local = getLocalRecipePath()
                .map(p -> {
                    try {
                        return Files.walk(p);
                    } catch (NoSuchFileException ignored) {
                    } catch (IOException e) {
                        System.err.printf("warning: unable to walk local recipe directory: %s%n", e);
                    }

                    return null;
                }).orElse(Stream.empty());

            var global = getGlobalRecipePath()
                .map(p -> {
                    try {
                        return Files.walk(p);
                    } catch (NoSuchFileException ignored) {
                    } catch (IOException e) {
                        System.err.printf("warning: unable to walk global recipe directory %s%n", e);
                    }

                    return null;
                }).orElse(Stream.empty())
        ) {
            String target = args[0];

            Optional<File> fileForRecipe = getFileForRecipe(local.skip(1), global.skip(1), target);
            if (fileForRecipe.isEmpty()) {
                System.err.printf("no recipe matched '%1$s' (%1$s.yaml) in either global or local folders%n", target);
                System.exit(1);
            }

            boolean ok = fileForRecipe
                .map(file -> {
                    ObjectMapper mapper = new YAMLMapper();
                    Mixins.applyTo(mapper);

                    try {
                        return mapper.readValue(file, new TypeReference<Step[]>() {});
                    } catch (IOException e) {
                        System.err.printf("unable to parse specified recipe: %s%n", e);
                        return null;
                    }
                }).map(steps -> {
                    Runner runner = new Runner(steps);
                    return runner.run(System.in, System.out, System.err);
                })
                .orElse(false);

            if (!ok) {
                System.exit(1);
            }
        }
    }

    private static Optional<File> getFileForRecipe(Stream<Path> local, Stream<Path> global, String sentinel) {
        return Stream.concat(local, global)
            .filter(p -> FilenameUtils.removeExtension(p.getFileName().toString()).equals(sentinel))
            .findFirst()
            .map(Path::toFile);
    }

    private static Optional<Path> getLocalRecipePath() {
        return Optional.of(Paths.get(".taffy"));
    }

    private static Optional<Path> getGlobalRecipePath() {
        String os = System.getProperty("os.name").toLowerCase();

        String configDir;
        if (os.contains("win")) {
            configDir = System.getenv("APPDATA");
        } else {
            String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");
            configDir = xdgConfigHome != null ? xdgConfigHome : System.getenv("HOME");
        }

        if (configDir == null) {
            return Optional.empty();
        }

        return Optional.of(Paths.get(configDir, ".taffy"));
    }
}
