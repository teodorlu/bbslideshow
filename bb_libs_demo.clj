(ns bb-libs-demo
  (:require [babashka.fs :as fs]
            [babashka.cli :as cli]
            [babashka.process :as process]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/fs tasks

;; Task: list all txt files from the current directory
fs/glob

;; Task: find just the file names of those txt files
fs/file-name

;; Task: list all txt files from the current directory
;; But those files MUST be instances of java.io.File
;;     sun.nio.fs.UnixPath is not allowed.
fs/file



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/cli tasks

;; Dispatch [] to cmd-slideshow and ["debug"] to cmd-debug

(defn cmd-slideshow [_] :cmd-slideshow)
(defn cmd-debug [_] :cmd-debug)

cli/dispatch

[]
["debug"]



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/process tasks

;; Open https://heartofclojure.eu with firefox
;;
;; (Firefox is found at /Applications/Firefox.app/Contents/MacOS/firefox on
;;  Teodor's computer)

process/shell
