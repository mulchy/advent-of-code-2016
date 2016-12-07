(ns problem-2.core)

;; part 1

;; buttons
;; 1 2 3
;; 4 5 6
;; 7 8 9

;; transition table
;;   U L D R
;; 1 1 1 4 2
;; 2 2 1 5 3
;; 3 3 2 6 3
;; 4 1 4 7 5
;; 5 2 4 8 6
;; 6 3 5 9 6
;; 7 4 7 7 8
;; 8 5 7 8 9
;; 9 6 8 9 9

(comment (def transitions {"1" {"U" "1"
                                "L" "1"
                                "D" "4"
                                "R" "2"}
                           "2" {"U" "2"
                                "L" "1"
                                "D" "5"
                                "R" "3"}
                           "3" {"U" "3"
                                "L" "2"
                                "D" "6"
                                "R" "3"}
                           "4" {"U" "1"
                                "L" "4"
                                "D" "7"
                                "R" "5"}
                           "5" {"U" "2"
                                "L" "4"
                                "D" "8"
                                "R" "6"}
                           "6" {"U" "3"
                                "L" "5"
                                "D" "9"
                                "R" "6"}
                           "7" {"U" "4"
                                "L" "7"
                                "D" "7"
                                "R" "8"}
                           "8" {"U" "5"
                                "L" "7"
                                "D" "8"
                                "R" "9"}
                           "9" {"U" "6"
                                "L" "8"
                                "D" "9"
                                "R" "9"}}))

;; part 2
;;     1
;;   2 3 4
;; 5 6 7 8 9
;;   A B C
;;     D

;;     U D L R
;; 1   1 3 1 1
;; 2   2 6 2 3
;; 3   1 7 2 4
;; 4   4 8 3 4
;; 5   5 5 5 6
;; 6   2 A 5 7
;; 7   3 B 6 8
;; 8   4 C 7 9
;; 9   9 9 8 9
;; A   6 A A B
;; B   7 D A C
;; C   8 C B C
;; D   B D D D

(def transitions {"1" {"U" "1"
                       "D" "3"
                       "L" "1"
                       "R" "1"}
                  "2" {"U" "2"
                       "D" "6"
                       "L" "2"
                       "R" "3"}
                  "3" {"U" "1"
                       "D" "7"
                       "L" "2"
                       "R" "4"}
                  "4" {"U" "4"
                       "D" "8"
                       "L" "3"
                       "R" "4"}
                  "5" {"U" "5"
                       "D" "5"
                       "L" "5"
                       "R" "6"}
                  "6" {"U" "2"
                       "D" "A"
                       "L" "5"
                       "R" "7"}
                  "7" {"U" "3"
                       "D" "B"
                       "L" "6"
                       "R" "8"}
                  "8" {"U" "4"
                       "D" "C"
                       "L" "7"
                       "R" "9"}
                  "9" {"U" "9"
                       "D" "9"
                       "L" "8"
                       "R" "9"}
                  "A" {"U" "6"
                       "D" "A"
                       "L" "A"
                       "R" "B"}
                  "B" {"U" "7"
                       "D" "D"
                       "L" "A"
                       "R" "C"}
                  "C" {"U" "8"
                       "D" "C"
                       "L" "B"
                       "R" "C"}
                  "D" {"U" "B"
                       "D" "D"
                       "L" "D"
                       "R" "D"}})

(defn move [current-position direction]
  (get-in transitions [current-position direction]))

(defn calculate-code [string]
  (->> string
       clojure.string/split-lines
       (map #(clojure.string/split % #""))
       (reduce (fn [acc val]
                 (let [initial (or (last acc) "5")]
                   (conj acc (reduce move initial val))))
               [])
       (apply str)))

;(calculate-code (slurp "input/problem2.txt"))
