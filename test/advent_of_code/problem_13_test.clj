(ns advent-of-code.problem-13-test
  (:require [advent-of-code.problem-13 :refer [hamming-weight display adjacent-rooms escape]]
            [clojure.test :refer [deftest is]]))

(deftest hamming-weight-test
  (is (= 3
         (hamming-weight 7)))
  (is (= 0
         (hamming-weight 0)))
  (is (= 14
         (hamming-weight (Math/floor (- (Math/pow 2 14) 1)))))
  (is (= 1
         (hamming-weight 256))))

(deftest display-test
  (is (= ".#.####.##
..#..#...#
#....##...
###.#.###.
.##..#..#.
..##....#.
#...##.###"
         (display 7 10 10))))

(deftest adjacent-rooms-test
  (is (= #{[1 0] [0 1]}
         (set (adjacent-rooms [0 0]))))
  (is (= #{[1 3] [0 2] [0 4]}
         (set (adjacent-rooms [0 3])))))

(deftest escape-test
  (is (= 11 (escape [7 4] 10))))
