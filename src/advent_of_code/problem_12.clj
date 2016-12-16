(ns advent-of-code.problem-12
  (:require [clojure.string :refer [split-lines split]]))

(def registers
  {:a 0
   :b 0
   :c 1
   :d 0})

(def program-counter 0)

(def program (split-lines (slurp "input/problem12.txt")))

(def computer {:program program
               :program-counter program-counter
               :registers registers})

(defn done? [{:keys [program program-counter]}]
  (>= program-counter
     (count program)))

(defn copy [computer x y]
    (-> computer
        (assoc-in [:registers y] (or (int-or-nil x)
                                     (get-in computer [:registers (keyword x)])))
        (update :program-counter inc)))

(defn increment  [computer x]
    (-> computer
      (update-in [:registers x] inc)
      (update :program-counter inc)))

(defn decrement [computer x]
  (-> computer
      (update-in [:registers x] dec)
      (update :program-counter inc)))

(defn int-or-nil [char]
  (try (Integer/valueOf (str char))
       (catch Exception e nil)))

(defn jump-unless-zero [computer x y]
  (if (zero? (or (int-or-nil x)
                 (get-in computer [:registers (keyword x)])))
    (update computer :program-counter inc)
    (update computer :program-counter + y)))

(defn parse [instruction]
  (when (nil? instruction)
        (throw (Exception. "Panic")))
  (let [[cmd x y] (split instruction #" ")]
    (case cmd
      "cpy" [copy x (keyword y)]
      "inc" [increment (keyword x)]
      "dec" [decrement (keyword x)]
      "jnz" [jump-unless-zero x (Integer/valueOf y)])))

(defn evaluate [{:keys [program program-counter] :as computer}]
  (let [[command & args] (parse (get program program-counter))]
    (apply command computer args)))

(defn compute [computer]
  (if (done? computer)
    (:a (:registers computer))
    (recur (evaluate computer))))
