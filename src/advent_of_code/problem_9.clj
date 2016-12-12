(ns advent-of-code.problem-9
  (:require [clojure.string :refer [starts-with? split-lines join trim]]))

;; common
(defn- update-context
  [context update-fn data left-to-read]
  (-> context
      (update :acc update-fn data)
      (assoc  :left-to-read left-to-read)))

(defn- handle-text [{:keys [left-to-read acc] :as context} update-fn transform-fn]
  (let [[acc left-to-read] (split-with #(not= \( %) left-to-read)]
    (update-context context update-fn (transform-fn acc) (join left-to-read))))

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

(defn- handle-marker [{:keys [left-to-read acc] :as context} update-fn transform-fn]
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
      (update-context context update-fn (transform-fn read) left-to-read))))

;; part 1

(defn- read-text [context]
  (handle-text context str join))

(defn- read-marker [context]
  (handle-marker context str identity))

(defn decompress [string]
  (loop [context {:acc ""
                  :left-to-read string}]
    (if (empty? (:left-to-read context))
      (:acc context)
      (recur (-> context
                 read-text
                 read-marker)))))

(->> (slurp "input/problem9.txt")
     split-lines
     join
     decompress
     count)

;; part 2
(declare memoized-decompressed-length)

(defn- count-text [context]
  (handle-text context + count))

(defn- count-marker [context]
  (handle-marker context + memoized-decompressed-length))

(defn decompressed-length [string]
  (loop [context {:acc 0
                  :left-to-read string}]
    (if (empty? (:left-to-read context))
      (:acc context)
      (recur (-> context
                 count-text
                 count-marker)))))

;; shoutout to bhuaman for giving me the memoize idea
(def memoized-decompressed-length (memoize decompressed-length))

(->> (slurp "input/problem9.txt")
     split-lines
     join
     decompressed-length)
