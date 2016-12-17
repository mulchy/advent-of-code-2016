(ns advent-of-code.problem-16-test
  (:require [advent-of-code.problem-16 :refer [little-number->big-number checksum generate-n-bits generate-checksum]]
            [clojure.test :refer [deftest is]]))

; 1 becomes 100.
; 0 becomes 001.
; 11111 becomes 11111000000.
; 111100001010 becomes 1111000010100101011110000

(deftest little-number->big-number-test
  (is (= "100" (little-number->big-number "1")))
  (is (= "001" (little-number->big-number "0")))
  (is (= "11111000000" (little-number->big-number "11111")))
  (is (= "1111000010100101011110000" (little-number->big-number "111100001010"))))

(deftest checksum-test
  (is (= "100" (checksum "110010110100"))))

(deftest generate-n-bits-test
  (is (= "10000011110010000111" (generate-n-bits 20 "10000"))))

(deftest generate-checksum-test
  (is (= "01100" (generate-checksum 20 "10000"))))
