#!/usr/bin/env bb

(ns bbslideshow
  (:require
   [babashka.fs :as fs]
   [babashka.process :as process]
   [babashka.cli :as cli]))

(def slide-top-padding 80)
(def default-slides-glob-pattern "**/*.txt")

(defn slide-files [root glob-pattern]
  (->>
   (fs/glob root glob-pattern)
   (sort-by str)))

(comment

  (def example-slides (slide-files "." default-slides-glob-pattern))

  (get example-slides 4)
  :rcf)

(def keymap
  {\j :bbslideshow/next-slide
   \k :bbslideshow/prev-slide
   \q :bbslideshow/quit
   \f :bbslideshow/find-slide
   \s :bbslideshow/interactive-babashka-shell
   ;; ESC appears to be \[
   \[ :bbslideshow/quit})

(comment
  (char 103)
  (int \k)
  (char 10)
  (char 127)
  (char 32))

(defn -enable-reading-char-by-char! []
  (process/shell "stty -icanon -echo"))

(defn -disable-reading-char-by-char! []
  (process/shell "stty icanon echo"))

(defmacro with-stdin-char-by-char
  [& body]
  `(try
     (-enable-reading-char-by-char!)
     ~@body
     (finally
       (-disable-reading-char-by-char!))))

(defmacro with-stdin-line-by-line
  [& body]
  `(try
     (-disable-reading-char-by-char!)
     ~@body
     (finally
       (-enable-reading-char-by-char!))))

(defn press-enter-to-continue []
  (println "Press ENTER to continue")
  (flush)
  (.read System/in))

(defn modeline [index the-slides]
  (str (apply str (repeat 20 "—"))
       "\n"
       (str (inc index) "/" (count the-slides))
       " "
       (fs/file-name (get the-slides index))))

(defn navigate-loop [slides-fn start-at-index]
  (loop [index start-at-index]
    (let [the-slides (slides-fn)]
      (when-let [slide (get the-slides index)]
        (dotimes [_ slide-top-padding]
          (println))
        (print (slurp (fs/file slide)))
        (println )
        (println (modeline index the-slides))
        (flush)
        (let [key (.read System/in)]
          (case (get keymap (char key) :bbslideshow/command-not-found)
            :bbslideshow/command-not-found
            (do
              (prn [:bbslideshow/command-not-found {:character (char key) :key key}])
              (press-enter-to-continue)
              (recur index))

            :bbslideshow/next-slide
            (let [next-index (inc index)]
              (if (contains? the-slides next-index)
                (recur next-index)
                (recur index)))

            :bbslideshow/prev-slide
            (let [next-index (dec index)]
              (if (contains? the-slides next-index)
                (recur next-index)
                (recur index)))

            :bbslideshow/quit
            nil

            (do
              (prn [:bbslideshow/command-found-but-not-handled
                    {:character (char key) :key key}])
              (press-enter-to-continue)
              (recur index))))))))

(defn cmd-slideshow [opts+args]
  (let [opts (:opts opts+args)
        root (:root opts ".")
        glob-pattern (:glob-pattern opts default-slides-glob-pattern)
        slides-fn #(->> (slide-files root glob-pattern)
                        (mapv str))]
    (with-stdin-char-by-char (navigate-loop slides-fn 0))))

(defn cmd-debug [_opts]
  (with-stdin-char-by-char
    (let [k (.read System/in)]
      (prn [k (type k)])
      (prn (get keymap (char k) :bbslideshow/command-not-found)))))

(defn cmd-doctor [_]
  (let [bin-status (fn [bin]
                     (if (fs/which bin)
                       "✓"
                       "⍻"))]
    (println "Required dependencies:")
    (doseq [bin ["bb"]]
      (println (bin-status bin) bin))
    (println)
    (println "Optional dependencies:")
    (doseq [bin ["fzf" "rlwrap"]]
      (println (bin-status bin) bin))))

(def dispatch-table
  [{:cmds ["doctor"] :fn cmd-doctor}
   {:cmds ["debug"] :fn cmd-debug}
   {:cmds [] :fn cmd-slideshow :args->opts [:root :glob-pattern]}])

(defn -main [& args]
  (cli/dispatch dispatch-table args))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
