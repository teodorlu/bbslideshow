#!/usr/bin/env bb

(ns bbslideshow
  (:require
   [babashka.fs :as fs]
   [babashka.process :as process]))

(def slide-top-padding 20)
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
    (dotimes [_ slide-top-padding]
      (println))
    (print (slurp (fs/file slide)))))

(defn enable-reading-char-by-char! []
  (process/shell "stty -icanon -echo"))

(defn disable-reading-char-by-char! []
  (process/shell "stty icanon echo"))

(defn -main [& args]
  (let [root (or (first args) ".")
        slides (->> (slide-files root slides-glob-pattern)
                    (mapv str))]
    (try
      (enable-reading-char-by-char!)
      (navigate-loop slides 0)
      (finally
        (disable-reading-char-by-char!)))))

(def keymap
  {\j :bbslideshow/next-slide
   \k :bbslideshow/prev-slide
   \q :bbslideshow/quit
   \f :bbslideshow/find-slide
   \s :bbslideshow/interactive-babashka-shell})

(char 103)
(int \k)

(defn -debug [& _args]
  (let [k (.read System/in)]
    (prn [k (type k)])
    (prn (get keymap (char k) :bbslideshow/command-not-found))))

(when (= *file* (System/getProperty "babashka.file"))
  (apply (if (= "--debug" (first *command-line-args*)) -debug -main)
         *command-line-args*))
