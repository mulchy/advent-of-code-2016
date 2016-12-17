(ns advent-of-code.problem-16
  (:require [clojure.string :refer [join replace]]))

; Call the data you have at this point "a".
; Make a copy of "a"; call this copy "b".
; Reverse the order of the characters in "b".
; In "b", replace all instances of 0 with 1 and all 1s with 0.
; The resulting data is "a", then a single 0, then "b".

(defn little-number->big-number [number]
  (-> number
      reverse
      join
      (replace #"0|1" {"0" "1"
                       "1" "0"})
      (#(str number 0 %))))

(defn checksum [string]
  (let [chksm (->> string
                   (partition 2)
                   (map (fn [[a b]]
                          (if (= a b)
                            "1"
                            "0")))
                   join)
        length (count chksm)]
    (if (even? length)
      (recur chksm)
      chksm)))

(defn generate-n-bits [n init]
  (let [length (count init)]
    (if (>= length n)
      (join (take n  init))
      (recur n (little-number->big-number init)))))

(defn generate-checksum [n init]
  (checksum (generate-n-bits n init)))

;(generate-checksum 35651584 "10001001100000001")
