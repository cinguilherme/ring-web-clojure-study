(ns project-uno.handlers)

(defn handler3 [request]
  {:body "this is the handler in the other namespace"
   :status 200
   :content-type "application/application-json"}
  )