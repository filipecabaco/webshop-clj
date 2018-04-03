(ns webshop.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer [json-body request]]
            [webshop.core :refer :all]))

(defn- mock-get-request
  [url]
  (request :get url))

;; (defn- mock-post-request
;;   [url]
;;   (-> (request :post url)
;;       (json-body {:url url})))

(deftest new-handler-get
  (testing "if exists, redirects to address"
    (let [id           (rand-int 1000)
          mock-request (mock-get-request (str "/url/" id))]
      (with-redefs [webshop.db/lookup (fn [_ _] id)]
        (let [handler  (new-handler {:db {}})
              response (handler mock-request)]
          (is (= 302 (:status response)))
          (is (= id (get (:headers response) "Location")))))))

  (testing "if does not exist, returns not found"
    (let [id           (rand-int 1000)
          mock-request (mock-get-request (str "/url/" id))]
      (with-redefs [webshop.db/lookup (fn [_ _] nil)]
        (let [handler  (new-handler {:db {}})
              response (handler mock-request)]
          (is (= 404 (:status response))))))))
