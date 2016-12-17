(ns advent-of-code.problem-14
  (:require [clojure.pprint :refer [pprint]]
            [clojure.string :refer [join]]
            [digest :refer [md5]]))

(def salt "zpqevtbw")

(defn md5-2016-times [string]
  (last (take 2017 (iterate md5 string))))

(def nth-hash (memoize
  (fn [salt n]
    (md5-2016-times (md5 (join [salt n]))))))

(defn find-triple [string]
  (last (re-find #"(.)\1\1" string)))

(defn triple? [string]
  (some? (find-triple string)))

(defn quint? [char string]
  (re-find (re-pattern (join (repeat 5 char)))
           string))

(defn quintuple-in-next-thousand?
  [salt n char]
  (seq
   (filter (partial quint? char)
           (map (partial nth-hash salt)
                (range (+ 1 n) (+ 1001 n))))))

(defn nth-key [salt n]
  (->> (range)
       (map-indexed #(vector %1 (nth-hash salt %2)))
       (filter #(triple? (second %)))
       (filter #(quintuple-in-next-thousand? salt (first %) (find-triple (second %))))
       (take n)
       (last)
       (first)))

(time (nth-key salt 64))
