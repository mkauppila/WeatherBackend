(ns weather.weather
  (:require [org.httpkit.client :as http]
            [cheshire.core :as cheshire]))


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

(defn request-weather [latitude longitude time unit-system language]
  (let [now (add-hours-to-timestamp 0 (current-unix-timestamp))
        request-url (form-request-url latitude longitude time unit-system language)]
    (http/get request-url)))

(defn string-to-json [string]
  (cheshire/parse-string string))

(defn meh [item]
  (println item)
  (string-to-json (:body item)))

; if nil, then return empty structure?
(defn form-head-item-response [item]
  (meh item))

(defn form-item-response [item]
  (meh item))

(defn current-weather [latitude longitude unit-system language]
  (let [current-time (current-unix-timestamp)
        later-today (add-hours-to-timestamp (current-unix-timestamp) 3)
        tomorrow (add-hours-to-timestamp (current-unix-timestamp) 24)]
    (let [weathers (map (fn [time] @(request-weather latitude longitude time unit-system language))
                        [current-time later-today tomorrow])]
      ; current-weather @(request-current-weather latitude longitude current-time unit-system language)
      {:body {
        :current (form-head-item-response (first weathers))
        :later-today (form-item-response (second weathers))
        :tomorrow (form-item-response (nth weathers 2))
      }
        ; the generate the rest
      }
      )))
