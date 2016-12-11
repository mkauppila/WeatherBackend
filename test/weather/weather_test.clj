(ns weather.weather-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [weather.weather :refer :all]))

(deftest test-weather
  (testing "base url formatting"
    (is (= (base-url-with-token) "https://api.darksky.net/forecast/test-token")))

  (testing "test adding hours to timestamp"
    (is (= (add-hours-to-timestamp 1 0) 3600))
    (is (= (add-hours-to-timestamp 2 0) 7200)))

  (testing "format the coordinates"
    (is (= (coordinate "0.9595" "0.63636") "0.9595,0.63636")))

  (testing "formatation of the entire DarkSky API call"
    (is (= (form-request-url "11" "22" "3300" "si" "fi")
           "https://api.darksky.net/forecast/test-token/11,22,3300?exclude=minutely,hourly,daily,alerts,flags&units=si&language=fi"))))
