(ns dispatch-demo
  (:require
   [babashka.cli :as cli]))

(defn status [opts+args]
  (prn "status")
  (prn opts+args))
(defn diff [opts+args]
  (prn "diff")
  (prn opts+args))

(declare dispatch-table)

(defn help [_]
  (println "Available commands:")
  (doseq [cmd dispatch-table]
    (prn (:cmds cmd))))

(def dispatch-table [{:cmds ["status"] :fn status}
                     {:cmds ["diff"] :fn diff}
                     {:cmds [] :fn help}])

(cli/dispatch dispatch-table
              *command-line-args*)

(cli/dispatch dispatch-table [])
