(ns weather.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as cheshire]
            [org.httpkit.client :as http]
            [weather.handler :refer :all]))

(def darksky-response
  (slurp "test/weather/data/input.json"))

(def get-current-response
  (slurp "test/weather/data/output.json"))

(deftest weather-api-e2e
  (testing "Test not found route"
    (let [response (app (mock/request :get "/api/not-here"))
          body     (:body response)]
      (is (= (:status response) 404))
      (is (= body "Not Found"))))

    (testing "test GET request for current weather"
      (with-redefs-fn {#'http/get (fn [url] (future {:body darksky-response :status 200}))}
        #(let [response (app (mock/request :get "/api/current?latitude=60.192059&longitude=24.945831&unit-system=auto&language=en"))]
          (is (= (:status response) 200))
          (is (= (cheshire/parse-string (:body response)) (cheshire/parse-string get-current-response)))))))
