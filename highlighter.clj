(ns highlighter
  "An incomplete console highlighter for Clojure code!"
  (:require [rewrite-clj.parser :as p]
            [rewrite-clj.node :as node]
            [clj-commons.ansi :as ansi]
            [clojure.string :as str]
            [utils :refer [console-println]]))

;; Taken from https://github.com/borkdude/blog/commit/c32bedf12e59ba79ccfdde76d0ba669e7cd39a5c

(defmulti node->html node/tag)

(defmethod node->html :map [node]
  (format "{%s}"
          (str/join "" (map node->html (:children node)))))

(defmethod node->html :list [node]
  (format "(%s)"
          (str/join "" (map node->html (:children node)))))

(defmethod node->html :vector [node]
  (format "[%s]"
          (str/join "" (map node->html (:children node)))))

(defmethod node->html :set [node]
  (format "#{%s}"
          (str/join "" (map node->html (:children node)))))

(defmethod node->html :token [node]
  (if (:k node)
    (ansi/compose [:green (str node)])
    (let [v (:value node)]
      (if (symbol? v)
        (ansi/compose [:bright-cyan (str node)])
        (str node)))))

(defmethod node->html :forms [node]
  (str/join "" (map node->html (:children node))))

(defmethod node->html :default [node]
  (if (:children node)
    (str/join "" (map node->html (:children node)))
    (str node)))

(defn highlight [code-str]
  (node->html (p/parse-string-all code-str)))

(comment
  (-> (highlight "(prn [:hello])")
      (console-println))
  (-> (highlight (slurp *file*))
      (console-println))
  (-> (highlight "(re-find #\"dude\" \"dude\")")
      (console-println))

  (highlight ":done")
  )
