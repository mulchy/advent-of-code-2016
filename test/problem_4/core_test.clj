(ns problem-4.core-test
  (:require [problem-4.core :refer [real? parse-input top-five]]
            [clojure.test :refer [deftest is]]))

(deftest parse-input-test
  (is (= [["aaaaabbbzyx" "123" "abxyz"]]
         (parse-input "aaaaa-bbb-z-y-x-123[abxyz]")))
  (is (= [["abcdefgh" "987" "abcde"]]
         (parse-input "a-b-c-d-e-f-g-h-987[abcde]")))
  (is (= [["notarealroom" "404" "oarel"]]
         (parse-input "not-a-real-room-404[oarel]")))
  (is (= [["totallyrealroom" "200" "decoy"]]
         (parse-input "totally-real-room-200[decoy]"))))

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
