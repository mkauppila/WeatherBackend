(ns weather.weather
  (:require [org.httpkit.client :as http]))

(defn env [name]
  (or (System/getenv name) "no-default"))

(defn base-url-with-token []
  (str (env "DARKSKY_URL") "/" (env "DARKSKY_TOKEN")))

(defn- current-unix-timestamp []
  (int (/ (System/currentTimeMillis) 1000)))

(defn add-hours-to-timestamp [hours timestamp]
  (+ (* hours 3600) timestamp))

(defn coordinate [latitude longitude]
  (str latitude "," longitude))

(defn exclude-sections [blocks]
  (str "exclude=" (clojure.string/join "," blocks)))

(defn form-request-url [latitude longitude time unit-system language]
  (str
    (base-url-with-token)
    "/"
    (coordinate latitude longitude)
    ","
    time
    "?"
    (exclude-sections ["minutely" "hourly" "daily" "alerts" "flags"])
    "&"
    (str "units=" unit-system)
    "&"
    (str "language=" language)))

(defn request-current-weather [latitude longitude unit-system language]
  (let [time (add-hours-to-timestamp 2 (current-unix-timestamp))
        request-url (form-request-url latitude longitude unit-system language time)]
    (println (str "hello: " request-url))
    (http/get request-url)))

(defn current-weather [latitude longitude unit-system language]
  (let [req @(request-current-weather latitude longitude unit-system language)]
    {:body (:body req)}))
