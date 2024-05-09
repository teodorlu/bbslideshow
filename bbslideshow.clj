#!/usr/bin/env bb

(ns bbslideshow
  (:require [babashka.fs :as fs]))

(def slide-top-padding 40)
(def slides-glob-pattern "**/*.slide.txt")

(defn slide-files [root glob-pattern]
  (->>
   (fs/glob root glob-pattern)
   (sort-by str)))

(comment

  (def example-slides (slide-files "." slides-glob-pattern))

  (get example-slides 4)
  :rcf)

(defn navigate-loop [slides current-index]
  (when-let [slide (get slides current-index)]
    (dotimes [_ 40]
      (println))
    (print (slurp (fs/file slide)))))

(defn -main [& args]
  (let [root (or (first args) ".")
        slides (slide-files root slides-glob-pattern)]
    (navigate-loop slides 0)))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
