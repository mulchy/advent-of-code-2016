(ns advent-of-code.util)

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn long-or-nil
  [string]
  (try (Long/valueOf string)
       (catch Exception e nil)))
