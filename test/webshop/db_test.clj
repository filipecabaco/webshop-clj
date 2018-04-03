(ns webshop.db-test
  (:require [clojure.test :refer :all]
            [webshop.db :as db]))
(deftest save
  (testing "adds new value to db"
    (let [key   (rand-int 100)
          value (rand-int 100)
          db    (atom {})]
      (db/save db key value)
      (is (= {key value} @db))))

  (testing "overrides value in db"
    (let [key             (rand-int 100)
          value (rand-int 100)
          old-value       {key (rand-int 100)}
          db              (atom old-value)]
      (db/save db key value)
      (is (= {key value} @db)))))

(deftest lookup
  (testing "existing value"
    (let [value (rand-int 100)
          key (rand-int 100)
          db (atom {key value})]
      (is (= value (db/lookup db key)))))

  (testing "non-existing value"
    (let [key (rand-int 100)
          db (atom {})]
      (is (nil? (db/lookup db key))))))
