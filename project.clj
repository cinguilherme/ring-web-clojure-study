(defproject project-uno "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [ring "1.8.2"]
                 ]
  :plugins  [[lein-ring "0.12.5"]]

  :ring {:handler project-uno.core/routing-handler
         :init project-uno.core/on-init
         :destroy project-uno.core/on-destroy}

  :repl-options {:init-ns project-uno.core})
