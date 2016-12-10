(ns problem-6.core
  (:require [clojure.string :refer [split-lines join split]]))

(defn error-correct
  [lines]
  (let [n     (count (first lines))
        input (split (join lines) #"")
        chars (reduce-kv (fn [acc index val]
                           (update acc (mod index n) #(conj % val)))
                         []
                         input)]
    (->> chars
         (map frequencies)
         (map #(sort-by (comp - val) %))
         (map first)
         (map key)
         join)))

(->> (slurp "input/problem6.txt")
     split-lines
     error-correct)
