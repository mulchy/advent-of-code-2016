(ns advent-of-code.problem-17
  (:require [advent-of-code.util :refer [transpose]]
            [clojure.string :refer [join]]
            [digest :refer [md5]]))

;    0 1 2 3
;   #########
;0  #S| | | #
;   #-#-#-#-#
;1  # | | | #
;   #-#-#-#-#
;2  # | | | #
;   #-#-#-#-#
;3  # | | |
;   ####### V


(def possible (set (for [x (range 4)
                         y (range 4)]
                     [x y])))

(defn dir->coord
  [dir]
  (case dir
    "U" [0 -1]
    "D" [0 1]
    "L" [-1 0]
    "R" [1 0]))

(def get-current-room
  (memoize
   (fn [path]
     "assumes we start at [0 0]"
     (reduce (fn [room dir]
               (mapv +
                     room
                     (dir->coord (str dir))))
             [0 0]
             path))))

(defn successful? [path]
  (when (= [3 3] (get-current-room path))
    path))

(defn possible-moves [passcode]
  (fn [path]
    (if (successful? path)
      []
      (->> (str passcode path)
           md5
           (take 4)
           (map (fn [dir char]
                  (when (contains? #{\b \c \d \e \f} char)
                    dir))
                ["U" "D" "L" "R"])
           (filter some?)
           (filter #(contains? possible (get-current-room (str path %))))
           (map #(str path %))))))

(defn next-moves [passcode]
  (let [possible-fn (possible-moves passcode)]
    (fn [list-of-paths]
      (->> list-of-paths
           (map possible-fn)
           flatten
           distinct))))

(defn longest-path-length [code]
  (->> (iterate (next-moves code) [""])
              (take-while #(not= 0 (count %)))
              flatten
              (filter successful?)
              last
              count))

(longest-path-length "pvhmgsws")
