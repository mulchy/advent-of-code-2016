(ns advent-of-code.problem-11
  (:require [advent-of-code.util :refer [transpose]]))

(def state
  {:elevator   0
   :chips      [0 0 0 2 2 2 2]
   :generators [0 0 0 1 1 1 1]})

(defn seq-contains? [coll e]
  (some? (some #{e} coll)))

(defn fried? [{:keys [chips generators]}]
  (let [radiated-chips   (map #(seq-contains? generators %) chips)
        shielded-chips   (map = chips generators)
        fried            (fn [radiated shielded] (and radiated
                                                      (not shielded)))]
    (some? (seq (filter true? (map fried radiated-chips shielded-chips))))))

(defn done? [{:keys [chips generators] :as state}]
  (and (apply = 3 chips)
       (apply = 3 generators)))

(defn moveable [coll elevator]
  (filter some? (map-indexed (fn [index floor]
                               (when (= floor elevator)
                                 index))
                             coll)))

(defn possible-elevator-moves [elevator]
  (case elevator
    0     [inc]
    3     [dec]
    (1 2) [inc dec]))

(defn power-set [coll]
  (loop [[x & xs] (seq coll)
         acc      [[]]]
    (if x
      (recur xs (concat acc
                        (map #(conj % x) acc)))
      acc)))

(defn chip-gen-compare [[chip1 gen1] [chip2 gen2]]
  (or (compare chip1 chip2)
      (compare gen1  gen2)))

(defn canonicalize [{:keys [chips generators] :as state}]
  "Sorts the chip and generator order to produce a cannonical form to reduce the number of branches to search"
  (let [[chips generators] (->>  (map vector chips generators)
                                 (sort chip-gen-compare)
                                 transpose)]
    (-> state
        (assoc :chips chips)
        (assoc :generators generators))))

(def possible-moves
  (memoize
   (fn  [{:keys [elevator chips generators] :as state}]
     (let [moveable-chips        (map #(conj [:chips] %)
                                      (moveable chips elevator))
           moveable-generators   (map #(conj [:generators] %)
                                      (moveable generators elevator))
           possible              (->> (concat moveable-chips moveable-generators)
                                      power-set
                                      (filter #(<= 1 (count %) 2)))
           elevator-moves        (possible-elevator-moves elevator)]
       (distinct (filter (complement fried?)
                         (for [moves       possible
                               direction   elevator-moves]
                           (canonicalize
                            (update
                             (reduce (fn [state move]
                                       (update-in state move direction))
                                     state
                                     moves)
                             :elevator direction)))))))))

(defn run [state]
  (loop [moves   (vector (canonicalize state))
         visited #{}
         steps   0]
    (let [moves   (->> moves
                       (map possible-moves)
                       flatten
                       distinct
                       (filter #(not (contains? visited %))))
          visited (into visited moves)
          steps   (inc steps)]
      (println "Taken " steps "steps, now checking " (count moves) " possible moves")
      (if (seq (filter done? moves))
        steps
        (recur moves visited steps)))))
