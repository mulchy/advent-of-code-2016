(ns advent-of-code.problem-4
  (:require [clojure.string :refer [join split split-lines trim]]))

(defn parse-line
  [line]
  (let [line                         (split line #"-")
        n                            (count line)
        [letters [num-and-checksum]] (split-at (- n 1) line)]
    (concat (vector (join letters)) (split num-and-checksum #"[\[|\]]"))))

(defn parse-input
  [input]
  (->> input
       split-lines
       (map (juxt identity parse-line))))

(defn top-five
  [coll]
  (->> coll
       frequencies
       (sort-by (juxt (comp - val) identity))
       (take 5)
       keys))

(defn real? [[letters _ checksum]]
  (= (join (top-five letters))
     checksum))

(defn rot-n [n character]
  (if (= \- character)
    " "
    (str (nth (drop-while #(neg? (compare % character))
                          (cycle "abcdefghijklmnopqrstuvwxyz"))
              n))))

(defn drop-checksum-and-sector-id
  [string]
  (re-find #"\D+(?=-)" string))

(->> (slurp "input/problem4.txt")
     parse-input
     (filter (fn [[_ parsed]]
               (real? parsed)))
     (map (fn [[original [_ sector-id _]]]
            (let [n (Integer/valueOf sector-id)]
              (vector (->> original
                           drop-checksum-and-sector-id
                           (map #(rot-n n %))
                           (apply str))
                      sector-id))))
     (filter (fn [[name _]]
               (= "northpole object storage" name)))
     first
     second)
