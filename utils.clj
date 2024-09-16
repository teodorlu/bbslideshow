(ns utils
  (:require
   [clojure.java.io :as io]))

(defn console-print
  "Forces the output to console, even with NREPL"
  [& ss]
  (binding [*out* (.writer (System/console))]
    (apply print ss)
    (flush)))

(defn console-println [& ss]
  (binding [*out* (.writer (System/console))]
    (apply println ss)))

(defn console-read-key []
  (.read (io/reader (.reader (System/console)))))

(comment
  (require '[babashka.process :as process]
           '[clojure.java.io :as io])

  (process/shell "stty -icanon -echo")

  (.read (io/reader (.reader (System/console))))

  ,)
