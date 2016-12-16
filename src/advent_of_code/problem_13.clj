(ns advent-of-code.problem-13
  (:require [clojure.pprint :refer [pprint]]
            [clojure.string :refer [join]]))

(defn polynomial [x y]
  (+ (* x x) (* 3 x) (* 2 x y) y (* y y)))

(defn hamming-weight [num]
  (Long/bitCount num))

(defn open? [[x y] constant]
  (-> (polynomial x y)
      (+ constant)
      hamming-weight
      even?))

(defn display
  [rows columns constant]
  (->> (vec (repeat rows (mapv (constantly nil) (range columns))))
       (map-indexed (fn [y row]
                      (map-indexed (fn [x _]
                                     (if (open? [x y] constant)
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

(defn escape [[x y] constant]
  (loop [moves   [[x y]]
         visited #{}
         steps   0]
    (let [moves   (->> moves
                       (map adjacent-rooms)
                       (apply concat)
                       distinct
                       (filter #(open? % constant))
                       (filter #(not (contains? visited %))))
          visited (into visited moves)
          steps   (inc steps)]
      (if (some #{[1 1]} moves)
        steps
        (recur moves visited steps)))))

(defn walk-n [[x y] n constant]
  (loop [moves   [[x y]]
         visited #{}
         steps   0]
    (let [moves   (->> moves
                       (map adjacent-rooms)
                       (apply concat)
                       distinct
                       (filter #(open? % constant))
                       (filter #(not (contains? visited %))))
          visited (into visited moves)
          steps   (inc steps)]
      (if (= n steps)
        visited
        (recur moves visited steps)))))

(escape [31 39] 1352)
(count (walk-n [1 1] 50 1352))
