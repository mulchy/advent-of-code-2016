(ns advent-of-code.problem-7
  (:require [clojure.string :refer [split split-lines]]))

(defn abba?
  [string]
  (->> string
       (partition 4 1)
       (filter (fn [[a b c d]]
                 (and (= a d)
                      (= b c)
                      (not= a b))))
       ((complement empty?))))

(defn supports-tls?
  [{:keys [supernet-sequences hypernet-sequences]}]
  (and ((complement not-any?) abba? supernet-sequences)
       (not-any? abba? hypernet-sequences)))

(defn split-brackets
  [string]
  (reduce-kv (fn [acc index val]
               (update acc (if (even? index)
                             :supernet-sequences
                             :hypernet-sequences)
                       conj val))
             {:hypernet-sequences []
              :supernet-sequences  []}
             (split string #"[\[|\]]")))

(defn- aba
  [string]
  (->> string
       (partition 3 1)
       (filter (fn [[first middle last]]
                 (and (= first last)
                      (not= first middle))))))

(defn- bab? [[a b _] string]
  (->> string
       (partition 3 1)
       (filter (fn [[first middle last]]
                 (and (= first last)
                      (not= first middle)
                      (= b first)
                      (= a middle))))
       ((complement empty?))))

(defn supports-ssl?
  [string]
  (let [split-string (split-brackets string)
        abas         (->> split-string
                          :supernet-sequences
                          (map aba)
                          (filter (complement empty?))
                          (apply concat))
        babs         (:hypernet-sequences split-string)]
    ((complement not-any?) true? (for [x abas
                                       y babs]
                                   (bab? x y)))))

(->> (slurp "input/problem7.txt")
     split-lines
     (filter supports-ssl?)
     count)
