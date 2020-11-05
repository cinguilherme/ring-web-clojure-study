(ns project-uno.core
  (:require [project-uno.handlers :as hand]) )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(defn example-handler [request]
  (def mydata {:name "guilherme" :last "Cintra"})

  {:body (pr-str request)                                                 ;;(pr-str request)
   :status 301
   :headers {"Set-cookie" "test=1"}
   :content-type "application/application-json"}
  )

(defn test1-handler [request] {:body "test1"})
(defn test2-handler [request] {:body "test2"})
(defn routing-handler [request]
  (def uri (:uri request))
  (cond
    (= uri "/test1") (test1-handler request)
    (= uri "/test2") (test2-handler request)
    (= uri "/test3") (hand/handler3 request)
    :else (example-handler request))
  )


(defn on-init []
  (println "hello init"))

(defn on-destroy []
  (println "hello destroy the app!"))