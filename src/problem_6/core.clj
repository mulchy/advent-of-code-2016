(ns problem-6.core
  (:require [clojure.string :refer [split-lines join split]]))

;; again, this function is super useful
(defn transpose [matrix]
  (apply map vector matrix))

(defn error-correct
  [lines]
  (->> lines
       transpose
       (map frequencies)
       (map #(sort-by val %))
       (map first)
       (map key)
       join))

(->> (slurp "input/problem6.txt")
     split-lines
     error-correct)
