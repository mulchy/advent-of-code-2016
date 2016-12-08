(ns problem-4.core
  (:require [clojure.string :refer [join split split-lines trim]]))

(defn parse-line
  [line]
  (let [line                         (split line #"-")
        n                            (count line)
        [letters [num-and-checksum]] (split-at (- n 1) line)]
    (concat (vector (join letters)) (split num-and-checksum #"[\[|\]]"))))

(defn parse-input
  [input]
  (->> input
       split-lines
       (map parse-line)))

(defn top-five
  [coll]
  (->> coll
       frequencies
       (sort-by (juxt (comp - val) identity))
       (take 5)
       keys))

(defn real? [[letters _ checksum]]
  (= (join (top-five letters))
     checksum))

(->> (slurp "input/problem4.txt")
     parse-input
     (filter real?)
     (reduce (fn [acc [_ sector-id _]]
               (+ acc
                  (Integer/valueOf sector-id)))
             0))
