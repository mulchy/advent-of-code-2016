(ns problem-7.core-test
  (:require [problem-7.core :refer [supports-tls? abba? split-brackets supports-ssl?]]
            [clojure.test :refer [deftest is]]))

(deftest supports-tls?-test
  (is (true?  (supports-tls? (split-brackets "abba[mnop]qrst"))))
  (is (false? (supports-tls? (split-brackets "abcd[bddb]xyyx"))))
  (is (false? (supports-tls? (split-brackets "aaaa[qwer]tyui"))))
  (is (true?  (supports-tls? (split-brackets "ioxxoj[asdfgh]zxcvbn")))))

(deftest abba?-test
  (is (true?  (abba? "abba")))
  (is (false? (abba? "aaaa")))
  (is (true?  (abba? "ioxxoj")))
  (is (false? (abba? "abcd"))))

(deftest supports-ssl?-test
  (is (true?  (supports-ssl? "aba[bab]xyz")))
  (is (false? (supports-ssl? "xyx[xyx]xyx")))
  (is (true?  (supports-ssl? "aaa[kek]eke")))
  (is (true?  (supports-ssl? "zazbz[bzb]cdb")))
  (is (true?  (supports-ssl? "aba[cbc]bcb"))))
