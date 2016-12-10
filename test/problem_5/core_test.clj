(ns problem-5.core-test
  (:require [problem-5.core :refer [collect-password]]
            [clojure.test :refer [deftest is]]))

(deftest collect-password-test
  (is (= {0 "a"
          1 "b"
          2 "c"
          3 "d"
          4 "e"
          5 "f"
          6 "g"
          7 "h"}
         (reduce collect-password
                 {}
                 [[0 "a"]
                  [1 "b"]
                  [3 "d"]
                  [1 "z"]
                  [2 "c"]
                  [4 "e"]
                  [5 "f"]
                  [6 "g"]
                  [7 "h"]]))))
