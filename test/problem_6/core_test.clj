(ns problem-6.core-test
  (:require [problem-6.core :refer [error-correct]]
            [clojure.test :refer [deftest is]]))

(deftest error-correct-test
  (is (= "advent"
         (error-correct ["eedadn"
                         "drvtee"
                         "eandsr"
                         "raavrd"
                         "atevrs"
                         "tsrnev"
                         "sdttsa"
                         "rasrtv"
                         "nssdts"
                         "ntnada"
                         "svetve"
                         "tesnvt"
                         "vntsnd"
                         "vrdear"
                         "dvrsen"
                         "enarar"]))))
