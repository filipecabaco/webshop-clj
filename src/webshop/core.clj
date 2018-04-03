(ns webshop.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.response :refer [created redirect]]
            [cheshire.core :as json]
            [compojure.core :refer [routes POST GET]]
            [compojure.route :refer [not-found]]
            [webshop.db]))



;;;;;;;;;;;;;;;;;;;;;;;;;
;; http handler
;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- parse-body
  [body]
  (-> body
      slurp
      (json/decode true)))

;; TODO - We need some mob programming here
(defn- shorten-url [app url])

;; TODO - We need some mob programming here
(defn- lookup-url [{:keys [db]} key]
  (let [result (webshop.db/lookup db key)]
    (if result
      (redirect result)
      (not-found "Not Found"))))

(defn new-handler
  [app]
  (routes
    (POST "/url" {body :body}
          (shorten-url app (:url (parse-body body))))
    (GET "/url/:url" {{url :url} :params}
         (lookup-url app url))
    (not-found "Not Found")))

;;;;;;;;;;;;;;;;;;;;;;;;;
;; webserver entrypoint
;;;;;;;;;;;;;;;;;;;;;;;;;

(def counter (atom 0))

(def db-instance (webshop.db/new-db))

(def handler
  (new-handler
    {:shorten-fn (fn [_] (str "short-" (swap! counter inc)))
     :db db-instance}))

(defn -main [& args]
  ; #' means "use the var"
  (jetty/run-jetty (wrap-reload #'handler) {:port 3000}))
