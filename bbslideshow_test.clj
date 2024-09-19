(ns bbslideshow-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [bbslideshow]))

(deftest render-slide-test
  (testing "Renders bold text"
    (is (= "This is [1m:bold[m"
           (bbslideshow/render-slide "This is `:bold`"))))
  (testing "Renders bold text"
    (is (=  "[1m:bold[m not bold [1m:bold[m"
           (bbslideshow/render-slide "`:bold` not bold `:bold`"))))
  (testing "Highlight code examples"
    (is (= "[32m:done[m"
           (bbslideshow/render-slide "```clojure
:done
```")))))
