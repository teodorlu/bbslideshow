# `bbslideshow`: plans for the future

Supports quick navigation forwards & back, selecting any slide, and quickly launching a babashka REPL.

Planned hotkeys:

| Hotkey | Action                |
|--------|-----------------------|
| `f`    | Fuzzy-find any slide  |
| `s`    | Enter a babashka REPL |

Extra optional dependencies:

| dependency | optional? | min version | how to install                      | purpose                                    |
|------------|-----------|-------------|-------------------------------------|--------------------------------------------|
| Fzf        | optional  | 0.50.0      | https://github.com/junegunn/fzf     | `fzf` powers fuzzy-find slide              |
| rlwrap     | optional  | 0.46.1      | https://github.com/hanslub42/rlwrap | `rlwrap` powers the built-in babashka REPL |

When running `bbslideshow` without optional dependencies installed, certain hotkeys will not work.
