(ns advent-of-code.problem-21-test
  (:require [advent-of-code.problem-21 :refer [parse swap-position swap-letter rotate-left rotate-right rotate-position reverse-positions move-position undo-rotate-position parse-and-eval invert scramble unscramble]]
            [clojure.test :refer [deftest is]]))

(deftest parse-test
  (is (= {:fn move-position
          :args ["2" "1"]}
         (parse "move position 2 to position 1")))
  (is (= {:fn swap-position
          :args ["0" "4"]}
         (parse "swap position 0 with position 4")))
  (is (= {:fn reverse-positions
          :args ["1" "6"]}
         (parse "reverse positions 1 through 6")))
  (is (= {:fn rotate-right
          :args ["4"]}
         (parse "rotate right 4 steps")))
  (is (= {:fn rotate-left
          :args ["6"]}
         (parse "rotate left 6 steps")))
  (is (= {:fn rotate-position
          :args ["a"]}
         (parse "rotate based on position of letter a")))
  (is (= {:fn swap-letter
          :args ["b" "a"]}
         (parse "swap letter b with letter a"))))

(deftest swap-position-test
  (is (= "cba"
         (swap-position "0" "2" "abc"))))

(deftest swap-letter-test
  (is (= "bcd"
         (swap-letter "a" "b" "acd"))))

(deftest rotate-left-test
  (is (= "bca"
         (rotate-left "1" "abc"))))

(deftest rotate-right-test
  (is (= "cab"
         (rotate-right "1" "abc"))))

(deftest rotate-position-test
  (is (= "ecabd" (rotate-position "b" "abdec"))))

(deftest reverse-positions-test
  (is (= "abcde"
         (reverse-positions "0" "4" "edcba"))))

(deftest move-position-test
  (is (= "bdeac"
         (move-position "1" "4" "bcdea"))))

(deftest undo-rotate-position-test
  (is (= "abcdefg"
         (undo-rotate-position "e" (rotate-position "e" "abcdefg")))))

(defn helper [password line]
  (invert (parse-and-eval password line)
          line))

(deftest invert-test
  (is (= "abcdefgh"
         (helper "abcdefgh" "move position 2 to position 1")))
  (is (= "abcdefgh"
         (helper "abcdefgh" "swap position 3 with position 0")))
  (is (= "abcdefgh"
         (helper "abcdefgh" "rotate based on position of letter g")))
  (is (= "abcdefgh"
         (helper "abcdefgh" "reverse positions 0 through 3")))
  (is (= "abcdefgh"
         (helper "abcdefgh" "swap letter d with letter a")))
  (is (= "abcdefgh"
         (helper "abcdefgh" "rotate right 4 steps")))
  (is (= "abcdefgh"
         (helper "abcdefgh" "rotate left 6 steps"))))


(deftest scramble-unscramble-test
  (is (= "abcdefgh"
         (let [password "abcdefgh"
               instructions ["move position 2 to position 1"
                             "move position 2 to position 5"
                             "move position 2 to position 4"
                             "swap position 0 with position 2"
                             "move position 6 to position 5"
                             "swap position 0 with position 4"
                             "reverse positions 1 through 6"
                             "move position 7 to position 2"
                             "rotate right 4 steps"
                             "rotate left 6 steps"
                             "rotate based on position of letter a"
                             "rotate based on position of letter c"
                             "move position 2 to position 0"
                             "swap letter d with letter a"
                             "swap letter g with letter a"
                             "rotate left 6 steps"
                             "reverse positions 4 through 7"
                             ]]
           (unscramble (scramble password instructions)
                       instructions)))))
