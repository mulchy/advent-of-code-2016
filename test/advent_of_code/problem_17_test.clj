(ns advent-of-code.problem-17-test
  (:require [advent-of-code.problem-17 :refer [dir->coord possible-moves get-current-room longest-path-length]]
            [clojure.test :refer [deftest is]]))

(deftest dir->coord-test
  (is (= [0 -1] (dir->coord "U"))))

(deftest possible-moves-test
  (is (= ["D"] ((possible-moves "hijkl") ""))))

(deftest get-current-room-test
  (is (= [2 2] (get-current-room "DRDR"))))

(deftest walk-maze-test
  (is (= 370
         (longest-path-length "ihgpwlah")))
  (is (= 492
         (longest-path-length "kglvqrro")))
  (is (= 830
         (longest-path-length "ulqzkmiv"))))
