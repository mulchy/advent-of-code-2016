(ns problem-1.core)

(def initial-data
  {:position [0 0]
   :direction [0 1]})

(defn rotate-left
  [[x y]]
  [(- y) x])

(defn rotate-right
  [[x y]]
  [y (- x)])

(defn parse-string
  [string]
  (let [dir (case (subs string 0 1)
              "R" :right
              "L" :left
              :unknown)
        num (Integer/parseInt (subs string 1))]
    {:dir dir
     :num num}))

(defn parse-directions
  [string]
  (map parse-string (clojure.string/split string #" ")))

(defn scale-vector
  [vector n]
  (map (partial * n) vector))

(defn taxi-reducer
  [acc val]
  (let [current-direction (:direction acc)
        current-position  (:position  acc)
        rotate (case (:dir val)
                 :right rotate-right
                 :left  rotate-left)
        new-direction (rotate current-direction)
        vector-to-add (scale-vector new-direction (:num val))
        new-position  (map + current-position vector-to-add)]
    {:position new-position
     :direction new-direction}))

(defn drive-taxi
  [directions starting-data]
  (reduce taxi-reducer
          starting-data
          directions))

(defn manhattan-distance
  [[x1 y1]
   [x2 y2]]
  (apply + (map #(Math/abs %)
                [(- x1 x2)
                 (- y1 y2)])))

(defn calculate-distance
  [directions]
  (manhattan-distance (:position initial-data)
                      (:position (drive-taxi (parse-directions directions) initial-data))))


(-> (slurp "input.txt")
    ;; I didn't really look at the input format and forgot it had commas in it
    (clojure.string/replace #"," "")
    clojure.string/trim
    calculate-distance)
