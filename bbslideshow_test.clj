(ns bbslideshow-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [bbslideshow]))

(deftest addition (is (= 2 (+ 1 1))))

(deftest render-test
  (testing "Renders bold text"
    (is (= "[1mhello[m"
           (bbslideshow/render "*hello*"))))
  (testing "Normal case"
    (is (= "hello" (bbslideshow/render "hello"))))
  (testing "Two bolds"
    (is (= "[1mbold 1[m and [1mbold 2[m"
         (bbslideshow/render "*bold 1* and *bold 2*"))))
  (testing "Evaluates code"
    (is (= "Code with expression\n\n    (inc 41)\n;; => 42"
           (bbslideshow/render "Code with expression

    (inc 41)")))))
