(ns problem-2.core-test
  (:require [problem-2.core :refer [move calculate-code]]
            [clojure.test :refer [deftest testing is]]))

(deftest move-test
  (testing "Move functions correctly"
    (is (= "1" (move "1" "U")))
    (is (= "1" (move "1" "R")))
    (is (= "3" (move "1" "D")))
    (is (= "1" (move "1" "L")))

    (is (= "5" (move "5" "U")))
    (is (= "6" (move "5" "R")))
    (is (= "5" (move "5" "D")))
    (is (= "5" (move "5" "L")))

    (is (= "9" (move "9" "U")))
    (is (= "9" (move "9" "R")))
    (is (= "9" (move "9" "D")))
    (is (= "8" (move "9" "L")))

    (is (= "3" (move "7" "U")))
    (is (= "8" (move "7" "R")))
    (is (= "B" (move "7" "D")))
    (is (= "6" (move "7" "L")))

    (is (= "B" (move "D" "U")))
    (is (= "D" (move "D" "R")))
    (is (= "D" (move "D" "D")))
    (is (= "D" (move "D" "L")))))

(deftest calculate-code-test
  (testing "Calculating the code"
    (is (= "5DB3" (calculate-code "ULL\nRRDDD\nLURDL\nUUUUD")))))
