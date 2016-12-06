(ns problem-1.core)

(def initial-data
  {:position [0 0]
   :direction [0 1]
   :visited #{[0 0]}})

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
  (as-> string s
      (clojure.string/split s #", ")
      (map clojure.string/trim s)
      (map parse-string s)))

(defn scale-vector
  [vector n]
  (map (partial * n) vector))

(defn taxi-reducer
  [acc val]
  (let [current-direction (:direction acc)
        current-position  (:position  acc)
        visited           (:visited   acc)
        rotate (case (:dir val)
                 :right rotate-right
                 :left  rotate-left)
        new-direction (rotate current-direction)
        vectors-to-add (map #(scale-vector new-direction %) (range 1 (+ 1 (:num val))))
        new-positions  (map  #(map + current-position %) vectors-to-add)
        [new [visited-previously & rest]] (split-with #(not (contains? visited %))
                                                      new-positions)]
    (if (some? visited-previously)
      (reduced {:position visited-previously
                :direction new-direction
                :visited (into visited new)})
      {:position (last new-positions)
       :direction new-direction
       :visited (into visited new)})))

(defn drive-taxi
  ([directions] (drive-taxi directions initial-data))
  ([directions starting-data]
   (reduce taxi-reducer
           starting-data
           directions)))

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

;(calculate-distance (slurp "input.txt"))
