(ns advent-of-code.problem-12-test
  (:require [advent-of-code.problem-12 :refer [computer compute]]
            [clojure.test :refer [deftest is]]))

(def state (assoc computer :program
                  ["cpy 41 a"
                   "inc a"
                   "inc a"
                   "dec a"
                   "jnz a 2"
                   "dec a"]))

(deftest compute-test
  (is (= 42
         (compute state))))
