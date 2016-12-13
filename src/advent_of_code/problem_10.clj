(ns advent-of-code.problem-10
  (:require [clojure.string :refer [starts-with? split-lines join]]))

(defn get-initial-state [lines]
  (->> lines
       (filter #(starts-with? % "value"))
       (map (comp reverse #(re-seq #"\d+" %)))
       (map (fn [[k v]] [(str "bot " k) [v]]))
       (map  #(apply hash-map %))
       (apply merge-with concat)))

(defn create-rules [lines]
  (->> lines
       (filter (complement #(starts-with? % "value")))
       (map #(re-seq #"bot \d+|output \d+" %))
       (map (fn [[bot low high]]
              {bot {:low low
                    :high high}}))
       (apply merge-with #(throw (Exception. "bad input")))))

(defn process [key state rules]
  (let [{low-bot :low high-bot :high}     (get rules key)
        [low-chip high-chip :as chips]    (sort-by
                                           #(Integer/valueOf %)
                                           (get state key))]
    (-> state
        (update low-bot conj low-chip)
        (update high-bot conj high-chip)
        (assoc  key []))))

(defn ready? [[k v]]
  (and (starts-with? k "bot")
       (= 2 (count v))))

(defn output? [[k v]]
  (starts-with? k "output"))

(defn run [state rules]
  (let [entries-to-process (filter ready? state)]
    (if (empty? entries-to-process)
      state
      (recur (process (key (first entries-to-process))
                      state
                      rules)
             rules))))

(defn zoom [input]
  (let [initial-state  (get-initial-state input)
        rules          (create-rules input)]
    (into {} (filter output? (run initial-state rules)))))

 (->> (split-lines (slurp "input/problem10.txt"))
        zoom
        (sort-by (fn [[k v]] (Integer/valueOf (re-find #"\d+" k))))
        (take 3)
        vals
        flatten
        (map #(Integer/valueOf %))
        (reduce *))
