(ns advent-of-code.problem-22
  (:require [clojure.string :refer [split-lines split]]
            [advent-of-code.util :refer [long-or-nil]]))

(def puzzle-input (drop 2 (split-lines (slurp "input/problem22.txt"))))

(def nodes (->> puzzle-input
                (map #(split % #"\s+"))
                (map (juxt first #(nth % 2) #(nth % 3)))
                (map (fn [[name used free]]
                       [name
                        (long-or-nil (re-find #"\d+" used))
                        (long-or-nil (re-find #"\d+" free))]))
                (map #(zipmap [:name :used :free] %))))

(count (distinct (filter some? (for [src    nodes
                                     target (remove #(= src %) nodes)]
                                 (let [free (:free target)
                                       used (:used src)]
                                   (if (and (not= 0 used)
                                            (> free
                                               used))
                                     [src target]
                                     nil))))))
