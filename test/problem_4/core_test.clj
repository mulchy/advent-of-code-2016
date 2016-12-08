(ns problem-4.core-test
  (:require [problem-4.core :refer [real? parse-line top-five rot-n]]
            [clojure.test :refer [deftest is]]))

(deftest parse-line-test
  (is (= ["aaaaabbbzyx" "123" "abxyz"]
         (parse-line "aaaaa-bbb-z-y-x-123[abxyz]")))
  (is (= ["abcdefgh" "987" "abcde"]
         (parse-line "a-b-c-d-e-f-g-h-987[abcde]")))
  (is (= ["notarealroom" "404" "oarel"]
         (parse-line "not-a-real-room-404[oarel]")))
  (is (= ["totallyrealroom" "200" "decoy"]
         (parse-line "totally-real-room-200[decoy]"))))

(deftest real?-test
  (is (true? (real? ["aaaaabbbzyx" "123" "abxyz"])))
  (is (true? (real? ["abcdefgh" "987" "abcde"])))
  (is (true? (real? ["notarealroom" "404" "oarel"])))
  (is (false? (real? ["totallyrealroom" "200" "decoy"]))))

(deftest top-five-test
  (is (= "abxyz"
         (apply str (top-five "aaaaabbbzyx"))))
  (is (= "abc"
         (apply str (top-five "aaaabbc")))))

(deftest rot-n-test
  (is (= " "
         (rot-n 837 \-)))
  (is (= "a"
         (rot-n 0 \a)))
  (is (= "z"
         (rot-n 25 \a)))
  (is (= "a"
         (rot-n 26 \a))))
