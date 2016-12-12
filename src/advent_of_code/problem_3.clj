(ns advent-of-code.problem-3
  (:require [clojure.string :refer [split split-lines trim]]))

(defn parse-input
  [input]
  (->> input
       split-lines
       (map trim)
       (map #(split % #"\s+"))
       (map (fn [strings] (map #(Integer/valueOf %) strings)))))

(defn triangle?
  [a b c]
  (and (> (+ a b) c)
       (> (+ a c) b)
       (> (+ b c) a)))

(def reorder-triple (juxt
                     (partial map first)
                     (partial map second)
                     (partial map #(nth % 2))))

;; way nicer solution from the interwebz
(defn transpose [matrix]
  (apply map list matrix))

(defn reorder-list [list]
  (->> list
       (partition 3)
       (mapcat transpose)))

(->> (slurp "input/problem3.txt")
     parse-input
     reorder-list
     (filter (partial apply triangle?))
     count)
