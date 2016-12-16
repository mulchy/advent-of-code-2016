(ns advent-of-code.problem-11-test
  (:require [advent-of-code.problem-11 :refer [possible-moves canonicalize fried? run]]
            [clojure.test :refer [deftest is]]))

(def state
  {:elevator   0
   :chips      [0 0]
   :generators [1 2]})

(deftest possible-move-test
  (is (= [{:elevator    1
           :chips      [0 1]
           :generators [2 1]}]
         (possible-moves state)))
  (is (= #{{:elevator   0
            :chips      [0 0]
            :generators [1 2]}
           {:elevator   2
            :chips      [0 2]
            :generators [2 2]}
           {:elevator   2
            :chips      [0 1]
            :generators [2 2]}}
         (set (possible-moves {:elevator   1
                               :chips      [1 0]
                               :generators [1 2]})))))
(deftest canonicalize-test
  (is (= state
         (canonicalize state)))
  (is (= {:elevator    1
          :chips      [0 1]
          :generators [1 2]}
         (canonicalize {:elevator    1
                        :chips      [0 1]
                        :generators [1 2]})))
  (is (= {:elevator    1
          :chips      [0 1]
          :generators [1 2]}
         (canonicalize {:elevator    1
                        :chips      [1 0]
                        :generators [2 1]})))
  (is (= {:elevator   0
          :chips      [0 0 0 2 2 2 2]
          :generators [0 0 0 1 1 1 1]}
         (canonicalize   {:elevator   0
                          :chips      [0 2 2 2 2 0 0]
                          :generators [0 1 1 1 1 0 0]}))))

(deftest fried?-test
  (is (not (fried?  {:elevator   0
                     :chips      [0 0]
                     :generators [1 2]})))
  (is (not (fried?  {:elevator   0
                     :chips      [1 0]
                     :generators [1 2]})))
  (is (fried?  {:elevator   0
                :chips      [0 1]
                :generators [1 2]})))

(deftest run-test
  (is (= 11
         (run state))))
