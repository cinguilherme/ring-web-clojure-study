(ns project-uno.html)

(defn emit [form]
  (if (vector? form)
    (str "<" (name (first form)) ">"
         (apply str (map emit (rest form)))
         "</" (name (first form)) ">")
    (str form)))