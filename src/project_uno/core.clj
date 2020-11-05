(ns project-uno.core
  (:require [project-uno.handlers :as hand]
            [ring.middleware.resource :as resource]
            [ring.middleware.file-info :as file-info]
            [ring.middleware.params]
            [ring.middleware.keyword-params]
            [project-uno.html :as html]
            [clojure.string]) )

(defn case-middleware [handler request]
  (let [request (update-in request [:uri] clojure.string/lower-case)
        response (handler request)]
    (if (string? (:body response))
      (update-in response [:body] clojure.string/capitalize)
      response)))

(defn wrap-case-middleware [handler]
  (fn [request] (case-middleware handler request)))


(defn not-found-middleware [handler]
  (fn [request]
    (or (handler request)
        {:status 404 :body (str "404 not found (with middleware): " (:uri request))})))

(defn simple-log-middleware [handler]
  (fn [{:keys [uri] :as request}]
    (println "request path " uri)
    (handler request))
  )

(defn example-handler [request]
  (def mydata {:name "guilherme" :last "Cintra"})

  {:body (pr-str request)                                                 ;;(pr-str request)
   :status 301
   :headers {"Set-cookie" "test=1"}
   :content-type "application/application-json"}
  )

(defn test1-handler [request] {:body "test1"})
(defn test2-handler [request] {:body "test2"})

(defn layout [contents]
  (html/emit
    [:html
     [:body
      [:h1 "Clojure is something.. i tell you"]
      [:p "this content comes from the layout function"]
      contents]]))

(defn form-handler [request]
  {:status 200
   :headers {"Content-type" "text/html"}
   :body (layout [:div
                  [:p "params: "]
                  [:pre (:params request)]
                  [:p "Query params:"]
                  [:pre (:query-params request)]
                  [:p "Form params"]
                  [:pre (:form-params request)]])
   }
  )

(defn routing-handler [request]
  (def uri (:uri request))
  (cond
    (= uri "/test1") (test1-handler request)
    (= uri "/test2") (test2-handler request)
    (= uri "/test3") (hand/handler3 request)
    (= uri "/") (example-handler request)
    (= uri "/form") (form-handler request)
    :else nil
    )
  )


(defn wrapping-handler [request]
  (if-let [resp (routing-handler request)]
    resp
    {:status 404 :body (str "not found: " (:uri request))})
  )

(def full-handler
  (-> routing-handler
      (resource/wrap-resource "public")
      not-found-middleware
      wrap-case-middleware
      simple-log-middleware
      ring.middleware.keyword-params/wrap-keyword-params
      ring.middleware.params/wrap-params))


(defn on-init []
  (println "hello init"))

(defn on-destroy []
  (println "hello destroy the app!"))