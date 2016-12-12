(ns advent-of-code.problem-9
  (:require [clojure.string :refer [starts-with? split-lines join trim]]))

(defn decompress [string]
  (loop [context {:read ""
                  :left-to-read string}]
    (if (empty? (:left-to-read context))
      (:read context)
      (recur (-> context
                 read-text
                 read-marker)))))

(defn- update-context
  [context read left-to-read]
  (-> context
      (update :read #(join [% read]))
      (assoc  :left-to-read left-to-read)))

(defn- read-text [{:keys [left-to-read read] :as context}]
  (let [[read left-to-read] (split-with #(not= \( %) left-to-read)]
    (update-context context (join read) (join left-to-read))))

(defn- split-with-excluding-match
  [f coll]
  (update (split-with f coll) 1 #(drop 1 %)))

(defn- marker-chars->struct [marker]
  (as-> marker $
    (filter #(and (not= \( %)
                  (not= \) %))
            $)
    (split-with-excluding-match #(not= \x %) $)
    (map (comp #(Integer/valueOf %) join) $)))

(defn- read-marker [{:keys [left-to-read read] :as context}]
  (if (empty? left-to-read)
    context
    (let [[marker left-to-read]          (split-with-excluding-match
                                          #(not= \) %)
                                          left-to-read)
          [chars-to-read num-repeats]    (marker-chars->struct marker)
          [read left-to-read]            (map join (split-at
                                                    chars-to-read
                                                    left-to-read))
          read                           (join (repeat num-repeats read))]
      (update-context context read left-to-read))))

(->> (slurp "input/problem9.txt")
     split-lines
     join
     decompress
     count)
