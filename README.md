# Taffy
Taffy is a stack based text manipulation tool.

## Recipes
`taffy` executes recipes, which are small lists of simple stack based
instructions (referred to as steps) written in YAML.

Recipes can be one of two types:

- **Local**, stored in the `.taffy` subdirectory of the working directory. 
- Or **global**, stored in:
  - `%APPDATA%\.taffy` on Windows
  - `$XDG_CONFIG_HOME/.taffy` or `$HOME/.taffy` on Unix like systems.

Documentation on the steps supported by Taffy can be found in `STEPS.md`.

## Install
The nightly (or Rolling Release) can be downloaded via the Actions tab,
provided that it has been less than 90 days since the last workflow run.

1. Go to [Actions → Rolling Release](https://github.com/jordanocokoljic/taffy/actions/workflows/rolling-release.yaml).
2. Select the most recent run (that is less than 90 days old).
3. Download the artifact from the 'Artifacts' section at the bottom of the page.
4. Copy `taffy.jar` and `taffy` (on Unix like systems) or `taffy.bat` (on
   Windows) into a folder in `PATH`.

Proper GitHub integrated releases will be coming soon.


## Usage
To use Taffy, there must be at least one local or global recipe. Here is an
example recipe:

```yaml
# File ./taffy/echo.yaml

- do: in:stdin
  prompt: "Message: "

- do: out:stdout
```

This recipe will prompt the user for some input, and then write that input to
standard out unmodified.

To execute this recipe, use:

```yaml
taffy echo
```

From either the parent of the `.taffy` directory if it has been saved as a
local recipe, or anywhere if it has been saved as a global recipe.

Note, recipes are identified by filename (without extension), and local recipes
will always be searched before global recipes, therefore if you have both a
local and  global `echo.yaml` recipe, Taffy will always execute the local
version.

## Building
To build Taffy locally, clone the repository, and run `mvn verify`, the built
`.jar` files will be contained within the `target` directory. The distribution
version will be named `taffy-{VERSION}-jar-with-dependencies.jar`.
