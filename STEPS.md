# Steps
These are the steps, and associated fields that Taffy currently supports,
grouped into broad categories.


## Table of Contents
- [Input](#input)
  - [`in:stdin`](#instdin)
  - [`in:text`](#intext)
- [Output](#output)
  - [`out:stdout`](#outstdout)
- [Encoding](#encoding)
  - [`encode:hex`](#encodehex)
  - [`encode:base64`](#encodebase64)
- [Decoding](#decoding)
  - [`decode:hex`](#decodehex)
  - [`decode:base64`](#decodebase64)
- [HMAC](#hmac)
  - [`hmac:sha256`](#hmacsha256)
- [Random](#random)
  - [`rand:bytes`](#randbytes)
- [Context](#context)
  - [`ctx:duplicate`](#ctxduplicate)


## Input
### `in:stdin`
```yaml
- do: in:stdin
  prompt: string
```

Prompts the user with the provided `prompt`, capturing the input up to the first
newline, and push the byte encoded value of the provided text (as UTF-8) to the
stack.

No padding is put between the end of the provided `prompt` before allowing the
user to provide input. 

If the provided `prompt` is `null` (from being set directly, or through omission)
the recipe will fail to parse.


### `in:text`
```yaml
- do: in:text
  text: string
```

Pushes the byte encoded value of the provided `text` (as UTF-8) to the stack.

If the provided `text` is `null` (from being set directly, or through omission)
the recipe will fail to parse.


## Output
### `out:stdout`
```yaml
- do: out:stdout
  prompt?: string
```

Pops the value on top of the stack and encodes it into a UTF-8 string, and
writing it to standard out.

A `prompt` may be provided, if it is then it will be prepended to the output
with no padding between the end of the `prompt` and the start of the output
value.


## Encoding
### `encode:hex`
```yaml
- do: encode:hex
```

Pops the value on top of the stack, encodes it using hexadecimal (or Base16)
encoding, and pushes the resulting bytes back on the stack.


### `encode:base64`
```yaml
- do: encode:base64
  charset: std-padded | std-raw | url-padded | url-raw
```

Pops the value on top of the stack, encodes it using the specified Base64
charset, and pushes the resulting bytes back on the stack.

`std-padded` and `std-raw` are based on [RFC 4648, Section 4](https://datatracker.ietf.org/doc/html/rfc4648#autoid-9)
with padding included in the output for the former, and omitted for the latter.

`url-padded` and `url-raw` are based on [RFC 4648, Section 5](https://datatracker.ietf.org/doc/html/rfc4648#autoid-10)
with padding included in the output for the former, and omitted for the latter.

If the provided `charset` is `null` (from being set directly, or through omission)
the recipe will fail to parse.


## Decoding
### `decode:hex`
```yaml
- do: decode:hex
```

Pops the value on top of the stack, decodes it, and pushes the resulting bytes
back on the stack.


### `decode:base64`
```yaml
- do: decode:base64
  charset: std-padded | std-raw | url-padded | url-raw
```

Pops the value on top of the stack, decodes it, and pushes the resulting bytes
back on the stack. Values with or without padding are accepted (regardless of
if the specified charset explicitly indicates otherwise).

`std-padded` and `std-raw` are based on [RFC 4648, Section 4](https://datatracker.ietf.org/doc/html/rfc4648#autoid-9).

`url-padded` and `url-raw` are based on [RFC 4648, Section 5](https://datatracker.ietf.org/doc/html/rfc4648#autoid-10).

If the provided `charset` is `null` (from being set directly, or through omission)
the recipe will fail to parse.


## HMAC
### `hmac:sha256`
```yaml
- do: hmac:sha256
  key: string
```

Pops the value on top of the stack, generates the HMAC-SHA256 hash for the value
with the provided `key` and pushes the resulting bytes back on the stack.

The `key` must be a 256 bit (32 byte) key, encoded as hexadecimal. If the `key`
is  `null` (from being set directly, or through omission), cannot be decoded, or
is invalid for some other reason the recipe will fail to parse.


## Random
### `rand:bytes`
```yaml
- do: rand:bytes
  num: number
```

Generates `number` cryptographically random bytes, pushing the resulting array
onto the stack.

If the provided `number` is `null` (from being set directly, or through
omission), or is negative the recipe will fail to parse.


## Context
### `ctx:duplicate`
```yaml
- do: ctx:duplicate
```

Duplicates the value on top of the stack.
