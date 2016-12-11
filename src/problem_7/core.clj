(ns problem-7.core
  (:require [clojure.string :refer [split split-lines]]))

(defn abba?
  [string]
  (->> string
       (partition 4 1)
       (filter (fn [[a b c d]]
                 (and (= a d)
                      (= b c)
                      (not= a b))))
       ((comp not empty?))))

(defn supports-tls?
  [{:keys [regular-sequences hypernet-sequences]}]
  (and (not (empty? (filter abba? regular-sequences)))
       (empty? (filter abba? hypernet-sequences))))

(defn split-brackets
  [string]
  (reduce-kv (fn [acc index val]
               (update acc (if (even? index)
                             :regular-sequences
                             :hypernet-sequences)
                       conj val))
             {:hypernet-sequences []
              :regular-sequences  []}
             (split string #"[\[|\]]")))

(->> (slurp "input/problem7.txt")
     split-lines
     (map split-brackets)
     (filter supports-tls?)
     count)
