(ns advent-of-code.problem-8-test
  (:require [advent-of-code.problem-8 :refer [rect display rotate-column rotate-row]]
            [clojure.test :refer [deftest is]]))

(def test-display (display 3 7))

(deftest rect-test
  (is (= [[true  true  true  false false false false]
          [true  true  true  false false false false]
          [false false false false false false false]]
         (rect test-display 3 2))))

(deftest rotate-column-test
  (is (= [[true  false true  false false false false]
          [true  true  true  false false false false]
          [false true  false false false false false]]
         (-> test-display
             (rect 3 2)
             (rotate-column 1 1)))))

(deftest rotate-row-test
  (is (= [[false false false false true  false true]
          [true  true  true  false false false false]
          [false true  false false false false false]]
         (-> test-display
             (rect          3 2)
             (rotate-column 1 1)
             (rotate-row    0 4)))))

(deftest wrap-test
  (is (= [[false true  false false true  false true]
          [true  false true  false false false false]
          [false true  false false false false false]]
         (-> test-display
             (rect          3 2)
             (rotate-column 1 1)
             (rotate-row    0 4)
             (rotate-column 1 1)))))
