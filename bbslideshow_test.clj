(ns bbslideshow-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [bbslideshow]))

(deftest addition (is (= 2 (+ 1 1))))

(deftest highlight-test
  (testing "highlights one backticked word"
    (is (= "We can now run [1mbbslideshow[m from a JVM"
           (bbslideshow/highlight "We can now run `bbslideshow` from a JVM"))))
  (testing "highlights two backticked words"
    (is (= (bbslideshow/highlight "We can now run `bbslideshow` `another` from a JVM")
           "We can now run [1mbbslideshow` `another[m from a JVM"))))
