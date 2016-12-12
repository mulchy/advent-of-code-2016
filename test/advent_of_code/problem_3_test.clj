(ns advent-of-code.problem-3-test
  (:require [advent-of-code.problem-3 :refer [triangle? reorder-triple reorder-list transpose]]
            [clojure.test :refer [deftest testing is]]))

(deftest triangle?-test
  (testing "the triangle predicate"
    (is (false? (triangle? 5 10 25)))
    (is (false? (triangle? 5 25 10)))
    (is (false? (triangle? 10 5 25)))
    (is (false? (triangle? 10 25 5)))
    (is (false? (triangle? 25 5 10)))
    (is (false? (triangle? 25 10 5)))

    (is (true? (triangle? 3 4 5)))
    (is (true? (triangle? 3 5 4)))
    (is (true? (triangle? 4 3 5)))
    (is (true? (triangle? 4 5 3)))
    (is (true? (triangle? 5 3 4)))
    (is (true? (triangle? 5 4 3)))))

(deftest reorder-triple-test
  (testing "reorder triple"
    (is (= [[1 4 7]
            [2 5 8]
            [3 6 9]]
           (reorder-triple [[1 2 3]
                            [4 5 6]
                            [7 8 9]])))))

(deftest transpose-test
  (testing "transpose"
    (is (= [[1 4 7]
            [2 5 8]
            [3 6 9]]
           (transpose [[1 2 3]
                       [4 5 6]
                       [7 8 9]])))))
