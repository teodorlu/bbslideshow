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

| dep      | min version | how to install                      | purpose                                    |
|----------|-------------|-------------------------------------|--------------------------------------------|
| Babashka | v1.3.190    | https://babashka.org/               | `bbslideshow` is written in Babashka       |
| Fzf      | 0.50.0      | https://github.com/junegunn/fzf     | `fzf` powers fuzzy-find slide              |
| rlwrap   | 0.46.1      | https://github.com/hanslub42/rlwrap | `rlwrap` powers the built-in babashka REPL |

`fzf` and `rlwrap` are optional dependencies.
When omitted, certain hotkeys will not work.

## Installing

Copy `bbslideshow.clj` from this folder to somewhere on your `PATH`.
Optionally rename it to `bbslideshow` if you don't want the `clj` extension.

I've installed `bbslideshow` on my machine as a symbolic link from `~/.local/bin/bbslideshow` to `bbslideshow.clj` in this folder:

    ln -sf "$PWD/bbslideshow.clj" ~/.local/bin/bbslideshow

(You may run `./install.sh` if you want to install `bbslideshow` in this exact way.)
