(ns advent-of-code.util)

(defn transpose [matrix]
  (apply mapv vector matrix))
