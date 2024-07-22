# `bbslideshow`

Write slides as plain text, then show them from a terminal.

Supports quick navigation forwards & back, selecting any slide, and quickly launching a babashka REPL.

## Usage

1. Write your slides with the `.slide.txt` extension.
2. Prefix a slide number.
3. Run `bbslideshow ROOT` to view a slideshow from `ROOT`. `ROOT` defaults to `.` (the current folder).

`bbslidewhow` will find all slides in the current directory, and show and navigate between them.

Once `bbslideshow` has started, you can use hotkeys to navigate between slides:

| Hotkey | Action                |
|--------|-----------------------|
| `j`    | Next slide            |
| `k`    | Previous slide        |
| `q`    | Quit                  |

## Required dependencies

| dependency | min version | how to install        | purpose                        |
|------------|-------------|-----------------------|--------------------------------|
| Babashka   | v1.3.190    | https://babashka.org/ | `bbslideshow` runs on Babashka |

## Installing

Copy `bbslideshow.clj` from this folder to somewhere on your `PATH`.
Optionally rename it to `bbslideshow` if you don't want the `clj` extension.

I've installed `bbslideshow` on my machine as a symbolic link from `~/.local/bin/bbslideshow` to `bbslideshow.clj` in this folder:

    ln -sf "$PWD/bbslideshow.clj" ~/.local/bin/bbslideshow

(You may run `./install.sh` if you want to install `bbslideshow` in this exact way.)

## Example slideshow

To view the example slideshow, run

    bbslideshow

from this folder.
