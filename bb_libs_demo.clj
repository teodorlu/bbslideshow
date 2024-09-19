(ns bb-libs-demo
  (:require [babashka.fs :as fs]
            [babashka.cli :as cli]
            [babashka.process :as process]
            [clojure.string :as str]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/cli tasks

;; Dispatch [] to cmd-slideshow and ["debug"] to cmd-debug

(defn cmd-slideshow [opts+args] opts+args)
(defn cmd-debug [opts+args] opts+args)
(defn cmd-textfiles [opts+args]
  (let [extensions (:ext (:opts opts+args))
        glob-expr (str/join "," extensions)]
    (fs/glob "." (str "**/*.{" glob-expr "}"))))

(->>
 (map str
      (cli/dispatch [{:cmds ["txt"] :fn cmd-textfiles
                      :spec {:ext {:coerce []}}}
                     {:cmds [] :fn cmd-slideshow}]
                    *command-line-args*))
 (run! println))

*command-line-args*

[]
["debug"]



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; babashka/process tasks

;; Open https://heartofclojure.eu with firefox
;;
;; (Firefox is found at /Applications/Firefox.app/Contents/MacOS/firefox on
;;  Teodor's computer)

process/shell
