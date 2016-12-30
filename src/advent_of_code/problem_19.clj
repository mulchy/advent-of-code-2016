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
