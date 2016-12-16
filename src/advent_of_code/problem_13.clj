(ns advent-of-code.problem-13
  (:require [clojure.pprint :refer [pprint]]
            [clojure.string :refer [join]]))

(defn polynomial [x y]
  (+ (* x x) (* 3 x) (* 2 x y) y (* y y)))

(def constant 1352)

(defn hamming-weight [num]
  (Long/bitCount num))

(defn open? [[x y]]
  (-> (polynomial x y)
      (+ constant)
      hamming-weight
      even?))

(defn display
  [rows columns]
  (->> (vec (repeat rows (mapv (constantly nil) (range columns))))
       (map-indexed (fn [y row]
                      (map-indexed (fn [x _]
                                     (if (open? [x y])
                                       "."
                                       "#"))
                                   row)))
       (map join)
       (join \newline)))


(defn adjacent-rooms [[x y]]
  (filter (partial not-any? neg?)
          [[(inc x) y]
           [x (inc y)]
           [(dec x) y]
           [x (dec y)]]))

(defn escape [x y]
  (loop [moves   [[x y]]
         visited #{}
         steps   0]
    (let [moves   (->> moves
                       (map adjacent-rooms)
                       (apply concat)
                       distinct
                       (filter open?)
                       (filter #(not (contains? visited %))))
          visited (into visited moves)
          steps   (inc steps)]
      (println "Taken " steps "steps, now checking " (count moves) " possible moves")
      (if (some #{[1 1]} moves)
        steps
        (recur moves visited steps)))))

(escape 31,39)
