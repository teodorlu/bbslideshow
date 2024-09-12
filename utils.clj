(ns utils)

(defn console-print
  "Forces the output to console, even with NREPL"
  [& ss]
  (binding [*out* (.writer (System/console))]
    (apply print ss)
    (flush)))

(defn console-println [& ss]
  (binding [*out* (.writer (System/console))]
    (apply println ss)))
