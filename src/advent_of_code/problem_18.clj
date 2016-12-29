(ns advent-of-code.problem-18
  (:require [clojure.string :refer [split join]]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]))

(def puzzle-input ".^^^^^.^^.^^^.^...^..^^.^.^..^^^^^^^^^^..^...^^.^..^^^^..^^^^...^.^.^^^^^^^^....^..^^^^^^.^^^.^^^.^^")

(defn input->row
  [input]
  (split input #""))

;Then, a new tile is a trap only in one of the following situations:
;Its left and center tiles are traps, but its right tile is not.
;Its center and right tiles are traps, but its left tile is not.
;Only its left tile is a trap.
;Only its right tile is a trap.
;In any other situation, the new tile is safe.
(defn tuple->tile
  [tuple]
  (case (join tuple)
    ("^^."
     ".^^"
     "^.."
     "..^") "^"

    "."))

(defn next-row
  [row]
  (let [padded (conj (vec (cons  "." row)) ".")]
    (->> padded
         (partition 3 1)
         (mapv tuple->tile))))

(defn next-input
  [input]
  (->> input
       input->row
       next-row
       join))

(defn create-n-rows [input n]
  (take n (iterate next-input input)))

(defn number-of-safe-tiles
  [input n]
  (->> (create-n-rows input n)
       join
       (filter #(= \. %))
       count))


;; specs


(defn- dot-or-carrot? [char]
  (case (str char)
    ("." "^") true
    false))

(s/def ::row
  (s/with-gen (s/coll-of (s/and string?
                                #(= 1 (count %))
                                dot-or-carrot?))
    #(gen/not-empty (gen/vector (s/gen #{"." "^"})))))

(s/def ::input
  (s/with-gen (s/and string?
                     not-empty
                     #(every? dot-or-carrot? %))
    #(gen/fmap
     join
     (gen/not-empty (gen/vector (s/gen #{"." "^"}))))))

(s/fdef input->row
        :args (s/cat :input ::input)
        :ret  ::row
        :fn   #(= (count (:ret %))
                  (count (-> % :args :input))))

(s/fdef next-row
        :args (s/cat :row ::row)
        :ret  ::row)

(s/fdef next-input
        :args (s/cat :input ::input)
        :ret ::input)

(s/def ::input-and-number
  (s/with-gen (s/cat :input ::input :n pos-int?)
    #(gen/tuple (s/gen ::input)
                (s/gen (s/int-in 0 40)))))
(s/fdef create-n-rows
        :args ::input-and-number
        :ret  (s/coll-of ::input)
        :fn   #(= (-> % :args :n)
                  (count (:ret %))))

(s/fdef number-of-safe-tiles
        :args ::input-and-number
        :ret  integer?)
