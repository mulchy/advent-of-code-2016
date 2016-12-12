(ns advent-of-code.problem-5
  (:require [digest :refer [md5]]
            [clojure.string :refer [join starts-with?]]))

(def input "cxdnnyjw")

(defn nth-hash [n]
  (md5 (join [input n])))

(defn int-or-nil [char]
  (try (Integer/valueOf (str char))
       (catch Exception e nil)))

(defn collect-password
  [password [position value]]
  (if  (< (count password) 8)
    (update password position #(or % value))
    (reduced password)))

(->> (range)
     (map nth-hash)
     (filter #(starts-with? % "00000"))
     (filter #(when-let [position (int-or-nil (nth % 5))]
                (and (>= position 0)
                     (<= position 8))))
     (map #(vector (int-or-nil (nth % 5)) (nth % 6)))
     (reduce collect-password {})
     sort
     vals
     join)
