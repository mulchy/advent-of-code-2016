(set-env!
 :resource-paths #{"src"}
 :dependencies   '[[org.clojure/clojure        "1.9.0-alpha14"]
                   ;[org.clojure/core.async     "0.2.395"]
                   [digest                     "1.4.5"]
                   ;dev
                   [adzerk/boot-test           "1.1.2"          :scope "test"]
                   [org.clojure/test.check     "0.9.0"          :scope "test"]])

(require '[adzerk.boot-test :as boot-test])

(deftask deps [])

(deftask testing []
  (merge-env! :resource-paths #{"test"})
  identity)

(deftask auto-test []
  (comp (testing)
        (watch)
        (speak)
        (boot-test/test)))

(deftask test []
  (comp (testing)
        (boot-test/test)))
