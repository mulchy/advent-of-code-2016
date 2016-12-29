(ns advent-of-code.problem-18-test
  (:require [advent-of-code.problem-18 :refer [input->row next-row next-input number-of-safe-tiles puzzle-input]]
            [clojure.test :refer [deftest is]]
            [clojure.spec.test :as stest]))

(defn specs-valid?
  "Runs test.check on given symbol and returns true if all checks passed"
  [symbol-to-test]
  (->> symbol-to-test
       stest/check
       stest/summarize-results
       (#(= (:total %)
             (:check-passed %)))))

(deftest specs-test
  (is (specs-valid? `input->row))
  (is (specs-valid? `next-row))
  (is (specs-valid? `next-input))

  ;; this test takes 5ever with large values of n
  ;(is (specs-valid? `create-n-rows))
  )

(deftest next-input-test
  (is (= ".^^^^"
         (next-input "..^^.")))
  (is (= "^^..^"
         (next-input ".^^^^"))))

(deftest number-of-safe-tiles-test
  (is (= 1989
         (number-of-safe-tiles puzzle-input 40))))
