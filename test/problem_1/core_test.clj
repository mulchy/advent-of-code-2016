(ns problem-1.core-test
  (:require [problem-1.core :refer [calculate-distance
                                    rotate-right
                                    rotate-left
                                    parse-string
                                    parse-directions
                                    drive-taxi
                                    initial-data]]
            [clojure.test :refer [deftest testing is]]))

(deftest calculate-distance-test
  (testing "Calculating the distance"
    (is (= 5  (calculate-distance "R2 L3")))
    (is (= 2  (calculate-distance "R2 R2 R2")))
    (is (= 12 (calculate-distance "R5 L5 R5 R3")))))

(deftest rotate-right-test
  (is (= [1 0] (rotate-right [0 1])))
  (is (= [0 -1] (rotate-right [1 0])))
  (is (= [-1 0] (rotate-right [0 -1])))
  (is (= [0 1] (rotate-right [-1 0]))))

(deftest rotate-left-test
  (is (= [-1 0] (rotate-left [0 1])))
  (is (= [0 1] (rotate-left [1 0])))
  (is (= [1 0] (rotate-left [0 -1])))
  (is (= [0 -1] (rotate-left [-1 0]))))

(deftest parse-string-test
  (is
  (= {:dir :right
       :num 42}
      (parse-string "R42"))))

(deftest parse-directions-test
  (is (=
       [(parse-string "R2") (parse-string "L2")]
       (parse-directions "R2 L2"))))

(deftest drive-taxi-test
  (is (=
       {:position [2 2] :direction [0 1]}
       (drive-taxi (parse-directions "R2 L2") initial-data))))
