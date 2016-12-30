(ns advent-of-code.problem-20
  (:require [clojure.string :refer [split-lines split]]
            [advent-of-code.util :refer [long-or-nil]]))

(defn make-list
  [input]
  (->> input
       split-lines
       (map #(split % #"-"))
       (map #(map long-or-nil %))
       (sort-by first)))

(every? (fn [[a b]] (> b a)) (make-list (slurp "input/problem20.txt")))

;; part 1
(reduce (fn [lowest-unblocked-ip [start end]]
          (if (<= start lowest-unblocked-ip)
            (inc end)
            (reduced lowest-unblocked-ip)))
        0
        (make-list (slurp "input/problem20.txt")))


;; part 2
(second (reduce (fn [[last-blocked number-of-allowed-ips] [start end]]
                  (if (<= start last-blocked)
                    [(max last-blocked end) number-of-allowed-ips]
                    [end                    (+ number-of-allowed-ips
                                               (- start (inc last-blocked)))]))
                [0 0]
                (make-list (slurp "input/problem20.txt"))))
