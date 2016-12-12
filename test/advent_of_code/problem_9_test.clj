(ns advent-of-code.problem-9-test
  (:require [advent-of-code.problem-9 :refer [decompress]]
            [clojure.test :refer [deftest is]]))

(deftest decompress-test
  (is (= "ADVENT"
         (decompress "ADVENT")))
  (is (= "ABBBBBC"
         (decompress "A(1x5)BC")))
  (is (= "XYZXYZXYZ"
         (decompress "(3x3)XYZ")))
  (is (= "(1x3)A"
         (decompress "(6x1)(1x3)A")))
  (is (= "X(3x3)ABC(3x3)ABCY"
         (decompress "X(8x2)(3x3)ABCY"))))
