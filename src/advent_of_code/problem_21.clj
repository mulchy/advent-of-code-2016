(ns advent-of-code.problem-21
  (:require [clojure.string :refer [starts-with? replace index-of split-lines]]
            [advent-of-code.util :refer [long-or-nil]]))

(defn swap-position [x y password]
  (let [[x y]  (sort (map long-or-nil [x y]))
        start  (subs password 0 x)
        middle (subs password (inc x) y)
        end    (subs password (inc y))]
    (str start
         (.charAt password y)
         middle
         (.charAt password x)
         end)))

(defn swap-letter [x y password]
  (-> password
      (replace x "TEMP")
      (replace y x)
      (replace "TEMP" y)))

(defn rotate-left [n password]
  (let [n (long-or-nil n)]
    (apply str (take (count password) (drop n (cycle password))))))

(defn rotate-right [n password]
  (let [n (long-or-nil n)]
    (apply str (reverse (take (count password) (drop n (cycle (reverse password))))))))

(defn rotate-position [x password]
  (let [index (index-of password x)]
    (rotate-right (inc (if (>= index 4)
                         (inc index)
                         index))
                  password)))

(defn reverse-positions [x y password]
  (let [[x y]  (sort (map long-or-nil [x y]))
        start  (subs password 0 x)
        middle (subs password x (inc y))
        end    (subs password (inc y))]
    (str start
         (apply str (reverse middle))
         end)))

(defn move-position [x y password]
  (let [[x y]  (map long-or-nil [x y])
        without-x  (str (subs password 0 x)
                        (subs password (inc x)))
        start (subs without-x 0 y)
        end   (subs without-x y)]
    (str start
         (.charAt password x)
         end)))

(defn parse [string]
  ;(println "Parsing:: " string)
  (condp re-find string
    #"swap position (.*) with position (.*)" :>>
    #(hash-map :fn swap-position
               :args (vec (drop 1 %)))

    #"swap letter (.*) with letter (.*)" :>>
    #(hash-map :fn swap-letter
               :args (vec (drop 1 %)))

    #"rotate left (.*) steps?" :>>
    #(hash-map :fn rotate-left
               :args (vec (drop 1 %)))

    #"rotate right (.*) steps?" :>>
    #(hash-map :fn rotate-right
               :args (vec (drop 1 %)))

    #"rotate based on position of letter (.*)" :>>
    #(hash-map :fn rotate-position
               :args (vec (drop 1 %)))

    #"reverse positions (.*) through (.*)" :>>
    #(hash-map :fn reverse-positions
               :args (vec (drop 1 %)))

    #"move position (.*) to position (.*)" :>>
    #(hash-map :fn move-position
               :args (vec (drop 1 %)))))

(defn parse-and-eval
  [password line]
  (->> (parse line)
       (#(apply (:fn %) (conj (:args %) password)))))

(defn scramble
  [initial instructions]
  (reduce parse-and-eval
          initial
          instructions))

(scramble "abcdefgh" (split-lines (slurp "input/problem21.txt")))

;; part 2
(defn undo-rotate-position
  [x password]
  ;; gah why is this so hard, just try them all
  (->> (iterate (fn [password] (rotate-right 1 password))
                password)
       (take (count password))
       (drop-while #(not= password (rotate-position x %)))
       first))

(defn undo-move-postion
  [x y password]
  (move-position y x password))

(defn inverse [fn]
  (condp = fn
    swap-letter swap-letter
    swap-position swap-position
    rotate-left rotate-right
    rotate-right rotate-left
    reverse-positions reverse-positions
    move-position undo-move-postion
    rotate-position undo-rotate-position))

(defn invert
  [password line]
  (-> (parse line)
      (#(apply (inverse (:fn %)) (conj (:args %) password)))))

(defn unscramble
  [initial instructions]
  (reduce invert
          initial
          (reverse instructions)))

(unscramble "fbgdceah" (split-lines (slurp "input/problem21.txt")))
