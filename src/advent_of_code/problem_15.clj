(ns advent-of-code.problem-15
  (:require [advent-of-code.util :refer [transpose]]
            [clojure.string :refer [split-lines]]))

(defn chinese-remainder-theorem [as ns]
  "Solves a system of modular congrunces of the form

     x ≡ a1 mod n1
     x = a2 mod n2
     ...
     x = am mod nm

where each n is pairwise coprime."
  (let [product (apply * ns)]
    (mod (reduce (fn [sum [a n]]
                    (let [p     (quot product n)
                          [_ s] (extended-euclidean-algorithm p n)]
                      (+ sum (* a s p))))
                  0
                  (map vector as ns))
         product)))

(defn extended-euclidean-algorithm [a b]
  (loop [s 0  old-s 1
         t 1  old-t 0
         r b  old-r a]
    (if (= 0 r)
      [old-r old-s old-t]
      (let [quotient    (quot old-r r)
            [old-r r]   [r (- old-r (* quotient r))]
            [old-s s]   [s (- old-s (* quotient s))]
            [old-t t]   [t (- old-t (* quotient t))]]
        (recur s old-s t old-t r old-r)))))

(defn parse [string]
  "Takes a description of a gear and converts it into a modular congruence
    x ≡ a mod n
represented by [a n]"
  (let [[disk-number modulo _ starting-position]
        (map #(Integer/valueOf %) (re-seq #"\d+" string))]
    [(- (+ disk-number starting-position)) modulo]))

(->> (slurp "input/problem15.txt")
     split-lines
     (map parse)
     (#(conj % [-7 11])) ;; part 2
     transpose
     (apply chinese-remainder-theorem))
