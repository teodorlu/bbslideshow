#!/usr/bin/env bb

(ns bbslideshow
  (:require
   [babashka.cli :as cli]
   [babashka.fs :as fs]
   [babashka.process :as process]
   [utils :refer [console-print console-println console-read-key]]
   [clj-commons.ansi :as ansi]
   [clojure.string :as str]
   [clojure.test :as t]))

(ansi/compose [:bold "hello"])
"[1mhello[m"

(def slide-top-padding 80)
(def default-slides-glob-patterns ["*.txt" "**/*.txt"])

(defn slide-files [root glob-patterns]
  (->>
   (mapcat (partial fs/glob root) glob-patterns)
   (sort-by str)
   vec))

(comment
  (def example-slides (slide-files "." default-slides-glob-patterns))
  (get example-slides 4)
  :rcf)

(def keymap
  {\j :bbslideshow/next-slide
   \n :bbslideshow/next-slide
   \k :bbslideshow/prev-slide
   \p :bbslideshow/prev-slide

   \q :bbslideshow/quit

   \s :bbslideshow/interactive-babashka-shell
   ;; ESC appears to be \[
   \[ :bbslideshow/quit
   \r :bbslideshow/refresh
   })

(comment
  (char 103)
  (int \k)
  (char 10)
  (char 127)
  (char 32))

(defn read-char-by-char! []
  (process/shell "stty -icanon -echo"))

(defn read-line-by-line! []
  (process/shell "stty icanon echo"))

(defn press-enter-to-continue []
  (console-println "Press ENTER to continue")
  (.read System/in))

(defn modeline [index the-slides]
  (str (apply str (repeat 20 "—"))
       "\n"
       (str (format "%2d" (inc index)) "/" (count the-slides))
       " "
       (fs/file-name (get the-slides index))))

(comment
  (re-find #"(?im)(.|\R)*" "first line
(defn render-slide [source-str]
  (str/replace source-str #\"`(.*?)`\"
               (ansi/compose [:green \"$1\"])))
second line")

  (re-pattern "(?im).*")




  )

(defn render-slide [source-str]
  (-> source-str
      (str/replace #"```clojure\n(.*?)`")
      (str/replace #"`(.*?)`"
                   (ansi/compose [:green "$1"]))))

(defn refresh [{:keys [index]}]
  (require '[bbslideshow] :reload)
  {:index index})

(defn navigate-loop [slides-fn start-at-index]
  (loop [index start-at-index]
    (let [the-slides (slides-fn)]
      (when-let [slide (get the-slides index)]
        (dotimes [_ slide-top-padding]
          (console-println))
        (console-print (render-slide  (slurp (fs/file slide))))
        (console-println)
        (console-println (modeline index the-slides))
        (let [key (console-read-key)]
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

            :bbslideshow/refresh
            (recur (:index (refresh {:index index})))

            :bbslideshow/quit
            nil

            (do
              (prn [:bbslideshow/command-found-but-not-handled
                    {:character (char key) :key key}])
              (press-enter-to-continue)
              (recur index))))))))

#_ (read-char-by-char!)
#_ (def slides-fn #(mapv str (slide-files "." default-slides-glob-patterns)))
#_ (navigate-loop slides-fn 0)

(defn cmd-slideshow [opts+args]
  (let [opts (:opts opts+args)
        root (:root opts ".")
        glob-patterns (if (:glob-pattern opts)
                        [(:glob-pattern opts)]
                        default-slides-glob-patterns)
        slides-fn #(->> (slide-files root glob-patterns)
                        (mapv str))]
    (try
      (read-char-by-char!)
      (navigate-loop slides-fn 0)
      (finally
        (read-line-by-line!)))))

(defn cmd-debug [_opts]
  (try
    (read-char-by-char!)
    (let [k (.read System/in)]
      (prn [k (type k)])
      (prn (get keymap (char k) :bbslideshow/command-not-found)))
    (finally
      (read-line-by-line!))))

(defn cmd-test [_]
  (require 'bbslideshow-test)
  (t/run-tests 'bbslideshow-test))

(def dispatch-table
  [{:cmds ["debug"] :fn cmd-debug}
   {:cmds ["test"] :fn cmd-test}
   {:cmds [] :fn cmd-slideshow :args->opts [:root :glob-pattern]}
   ])

(defn -main [& args]
  (cli/dispatch dispatch-table args))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
