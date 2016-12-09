(ns problem-5.core
  (:require [digest :refer [md5]]
            [clojure.string :refer [join starts-with?]]))

(def input "cxdnnyjw")

(defn nth-hash [n]
  (md5 (join [input n])))

(->> (range)
     (map nth-hash)
     (filter #(starts-with? % "00000"))
     (take 8)
     (map #(nth % 5))
     join)

