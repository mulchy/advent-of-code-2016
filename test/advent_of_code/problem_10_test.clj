(ns advent-of-code.problem-10-test
  (:require [advent-of-code.problem-10 :refer [zoom]]
            [clojure.test :refer [deftest is]]))
(def test-input
"value 5 goes to bot 2
bot 2 gives low to bot 1 and high to bot 0
value 3 goes to bot 1
bot 1 gives low to output 1 and high to bot 0
bot 0 gives low to output 2 and high to output 0
value 2 goes to bot 2")

(deftest zoom-test
  (let [output (zoom (clojure.string/split-lines test-input))]
    (println output)
    (is (= ["5"] (get output "output 0")))
    (is (= ["2"] (get output "output 1")))
    (is (= ["3"] (get output "output 2")))))
