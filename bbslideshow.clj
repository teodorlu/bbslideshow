#!/usr/bin/env bb

(ns bbslideshow
  (:require [babashka.fs :as fs]))

(defn -main [& args]
  (let [root (or (first args) ".")]
    (prn (fs/canonicalize (fs/absolutize (fs/file root))))))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
