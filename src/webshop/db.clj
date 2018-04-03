(ns webshop.db)

(defn save
  [db key value]
  (swap! db conj {key value}))

(defn lookup
  [db key]
  (get @db key))

(defn new-db
  []
  (atom {}))
