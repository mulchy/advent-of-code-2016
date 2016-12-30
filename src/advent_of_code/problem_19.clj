(ns advent-of-code.problem-19)

;; <3 math strikes again

;; http://oeis.org/A006257

(defn floor-log2 [n]
  (Math/floor (/ (Math/log10 n)
                 (Math/log10 2))))

(defn formula [n]
  (int (+ 1 (* 2 (- n (Math/pow 2 (floor-log2 n)))))))

(map formula (range 1 18))

(formula 3012210)


(defn drop-middle
  [coll]
  (let [middle (quot (count coll)
                     2)
        [first rest] (split-at middle coll)]
    (concat first (drop 1 rest))))

(defn rotate
  [coll]
  (take (count coll) (drop 1 (cycle coll))))

(def drop-middle-and-rotate
  (comp rotate drop-middle))

(defn steal-presents [n]
  (loop [coll (range 1 (inc n))]
    (if (= 1 (count coll))
      (first coll)
      (recur (drop-middle-and-rotate coll)))))

(map steal-presents (range 1 300))

;; the above fn is wayyy too slow to process the whole problem,
;; but gives us the insight we need to write a faster one

; starting from a group of 4 elves, the elf who wins follows this pattern
; 1 2 3
; 5 7 9

; 1  2  3  4  5  6  7  8  9
; 11 13 15 17 19 21 23 25 27

; n terms of incrementing by 1, followed by n terms of incrementing by 2, where n is some power of 3

; each time this happens we add the following number of new terms to the sequence
; 6 + 18 + 54 + ...
; 6 + 6*3 + 6*3^2 + ...

; sum of geometric series = a * (1 - r^n) / (1 - r)

(defn sum-of-n-terms [n]
  (* 6  (/ (- 1 (Math/pow 3 n))  (- 1 3))))

(loop [n 3]
  (let [sum (sum-of-n-terms n)]
    (if (> sum 3012210)
      n
      (recur (inc n)))))

; thus we need 13 of these to get to > the puzzle input

(nth (loop [n 1
              winning-elves []]
         (if (> n 13)
           winning-elves
           (let [power (Math/pow 3 n)]
             (recur (inc n)
                    (concat winning-elves
                            (range 1 (inc power))
                            (range (int (+ power 2)) (int (inc (Math/pow 3 (+ n 1)))) 2))))))
     ;; also remember we started at 4
     (- 3012210 4))

;; there is probably a nice closed form solution to this one as well, but I didn't figure out what it is
