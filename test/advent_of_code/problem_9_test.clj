(ns advent-of-code.problem-9-test
  (:require [advent-of-code.problem-9 :refer [decompress decompressed-length]]
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

(deftest decompressed-length-test
  (is (= (count "XYZXYZXYZ")
         (decompressed-length "(3x3)XYZ")))
  (is (= (count "XABCABCABCABCABCABCY")
         (decompressed-length "X(8x2)(3x3)ABCY")))
  (is (= 241920
         (decompressed-length "(27x12)(20x12)(13x14)(7x10)(1x12)A")))
  (is (= 445
         (decompressed-length "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"))))
