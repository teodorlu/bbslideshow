# `bbslideshow`

Write slides as plain text, then show them from a terminal.

Supports quick navigation forwards & back, selecting any slide, and quickly launching a babashka REPL.

## Usage

1. Write your slides with the `.slide.txt` extension.
2. Prefix a slide number.
3. Run `bbslideshow`.

`bbslidewhow` will find all slides in the current directory, and show and navigate between them.

Once `bbslideshow` has started, you can use hotkeys to navigate between slides:

| Hotkey | Action                |
|--------|-----------------------|
| `j`    | Next slide            |
| `k`    | Previous slide        |
| `f`    | Fuzzy-find any slide  |
| `s`    | Enter a babashka REPL |
| `q`    | Quit                  |

## Required dependencies

`babashka v1.3.190` or a later version.
Installation instructions for Babashka may be found at https://babashka.org/.

`fzf 0.50.0` or a later version.
Installation instructions for Fzf may be found at https://github.com/junegunn/fzf.

## Installing

Copy `bbslideshow.clj` from this folder to somewhere on your `PATH`.
Optionally rename it to `bbslideshow` if you don't want the `clj` extension.

I've installed `bbslideshow` on my machine as a symlink from `~/.local/bin/bbslideshow` to `bbslideshow.clj` in this folder:

    ln -s bbslideshow.clj ~/.local/bin/bbslideshow
