(ns bb-libs-demo
  (:require [babashka.fs :as fs]
            [babashka.cli :as cli]
            [babashka.process :as process]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/fs tasks

;; Task: list all txt files from the current directory
(fs/glob "." "*/*.txt")
(fs/list-dir "." "*.txt")

;; Task: find just the file names of those txt files
(fs/file-name (fs/absolutize (fs/file "README.md")))
(def files (fs/glob "." "*/*.txt"))
(map fs/file-name files)

;; Task: list all txt files from the current directory
;; But those files MUST be instances of java.io.File
;;     sun.nio.fs.UnixPath is not allowed.
(map fs/file files)

(slurp (fs/file  (first files)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/cli tasks

;; Dispatch [] to cmd-slideshow and ["debug"] to cmd-debug

(defn cmd-slideshow [_] :cmd-slideshow)
(defn cmd-debug [_] :cmd-debug)

*command-line-args*

(cli/dispatch [{:cmds ["debug"] :fn cmd-debug}
               {:cmds [] :fn cmd-slideshow}]
              [])

[]
["debug"]



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/process tasks

;; Open https://heartofclojure.eu with firefox
;;
;; (Firefox is found at /Applications/Firefox.app/Contents/MacOS/firefox on
;;  Teodor's computer)

(process/shell "/Applications/Firefox.app/Contents/MacOS/firefox" "https://heartofclojure.eu")

(fs/which "firefox")
