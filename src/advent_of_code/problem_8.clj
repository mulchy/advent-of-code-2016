(ns advent-of-code.problem-8
  (:require [clojure.string :refer [starts-with? split-lines]]
            ))

(defn display
  [rows columns]
  (vec (repeat rows (vec (map (constantly false) (range columns))))))

(defn light [display x y]
  (update-in display [y x] (constantly true)))

(defn rect [display x y]
  (reduce (fn [display [x y]]
            (light display x y))
          display
          (for [x (range x)
                y (range y)]
            [x y])))

(defn rotate-row [display row n]
  (update display row (fn [row]
                        (let [length (count row)]
                          (->> row
                               reverse
                               cycle
                               (drop n)
                               (take length)
                               reverse
                               vec)))))

;; mvp function of this whole challenge
(defn transpose [matrix]
  (vec (apply map vector matrix)))

(defn rotate-column [display column n]
  (transpose (rotate-row (transpose display) column n)))

(defn parse-line-and-draw [display line]
  (cond
    (starts-with? line "rect")
    (apply rect display (map #(Integer/valueOf %) (re-seq #"[0-9]+" line)))

    (starts-with? line "rotate row")
    (apply rotate-row display (map #(Integer/valueOf %) (re-seq #"[0-9]+" line)))

    (starts-with? line "rotate column")
    (apply rotate-column display (map #(Integer/valueOf %) (re-seq #"[0-9]+" line)))))

(->> (slurp "input/problem8.txt")
     split-lines
     (reduce parse-line-and-draw
             (display 6 50))
     flatten
     (filter true?)
     count)
