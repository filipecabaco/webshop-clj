(defproject webshop "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.3"]
                 [compojure "1.6.0"]
                 [cheshire "5.8.0"]]

  :main webshop.core

  :profiles {:dev {:source-paths ["dev"]
                   ;; Changed to start on webshop namespace
                   :repl-options {:init-ns webshop.core}
                   ;; Added to have watch capabilities
                   :plugins  [[com.jakemccrary/lein-test-refresh "0.22.0"]]
                   :dependencies [[ring/ring-devel "1.6.3"]
                                  ;; Adds capability to mock requests
                                  [ring/ring-mock "0.3.2"]]}})
