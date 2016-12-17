(ns advent-of-code.problem-15-test
  (:require [advent-of-code.problem-15 :refer [parse chinese-remainder-theorem extended-euclidean-algorithm]]
            [clojure.test :refer [deftest is]]))

(deftest extended-euclidean-algorithm-test
  (is (= [2 -9 47] (extended-euclidean-algorithm 240 46))))

(deftest chinese-remainder-theorem-test
  (is (= 23 (chinese-remainder-theorem [2 3 2] [3 5 7]))))

(deftest parse-test
  (is (= [-11 13] (parse "Disc #1 has 13 positions; at time=0, it is at position 10."))))
