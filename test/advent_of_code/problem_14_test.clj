(ns advent-of-code.problem-14-test
  (:require [advent-of-code.problem-14 :refer [md5-2016-times nth-key find-triple triple? quint? quintuple-in-next-thousand?]]
            [clojure.test :refer [deftest is]]))

(def salt "abc")

(deftest triple?-test
  (is (triple? "aaa"))
  (is (not (triple? "abc")))
  (is (triple? "abcdeeedfsj")))

(deftest find-triple-test
  (is (= "a" (find-triple "aaa")))
  (is (= nil (find-triple "abc")))
  (is (= "e" (find-triple "abcdeeedfsj"))))

(deftest quint?-test
  (is (quint? "a" "aaaaa"))
  (is (not (quint? "a" "aaaa")))
  (is (quint? "a" "aaaaaaaaaaaa")))

(deftest quintuple-in-next-thousand?-test
  (is (not (quintuple-in-next-thousand? salt 5 "2")))
  (is (quintuple-in-next-thousand? salt 10 "e")))

(deftest md5-2016-times-test
  (is (= "a107ff634856bb300138cac6568c0f24"
         (md5-2016-times "577571be4de9dcce85a041ba0410f29f"))))

(deftest nth-key-test
    (is (= 22551 (nth-key salt 64))))
