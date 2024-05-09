#!/usr/bin/env bb

(ns bbslideshow
  (:require [babashka.fs :as fs]))

(def slides-glob-pattern "**/*.slide.txt")

(defn slide-files [root glob-pattern]
  (->>
   (fs/glob root glob-pattern)
   (sort-by str)))

(comment

  (slide-files "." slides-glob-pattern)
  :rcf)

(defn -main [& args]
  (let [root (or (first args) ".")]
    (prn (fs/canonicalize (fs/absolutize (fs/file root))))))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
