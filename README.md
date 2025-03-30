# Taffy
Taffy is a stack based text manipulation tool.

## Recipes
Taffy works by executing `recipes` which are a set of instructions (each of which is referred to as a `step`).

Recipes are written in YAML, and can be either:

 - **Local**, meaning they are stored under `.taffy`, within the calling directory.
 - **Global**, meaning they are stored in:
   - `%APPDATA/.taffy` on Windows
   - `$XDG_CONFIG_HOME/.taffy` or `$HOME/.taffy` on Unix like operating systems

Here is an example recipe:

```
- do: in:text
  text: "Hello, world"
  
- do: encode:hex

- do: out:stdout
```

Running this recipe results in `48656c6c6f2c20776f726c64` being printed to standard out - the hex representation of
the string `"Hello, world"`.


## Steps
Taffy supports a variety of steps, each of which will push or pop values on and off the stack.

| Step            | Fields                                                    | Purpose                                                                                                                    |
|-----------------|-----------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| `in:text`       | `text: string`                                            | Pushes the provided text onto the stack.                                                                                   |
| `encode:hex`    | None                                                      | Takes the value on the top of the stack, encodes it as a hexadecimal string, pushing the result back onto the stack.       |
| `encode:base64` | `charset: std-padded \| std-raw \| url-padded \| url-raw` | Takes the value on the top of the stack, encodes it with the given base64 alphabet, pushing the result back onto the stack |
| `out:stdout`    | None                                                      | Takes the value on the top of the stack, writing it to standard out.                                                       |
