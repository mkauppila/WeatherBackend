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

(defn execute-request [url]
  (let [response @(http/get url)]
    (if (= (:status response) 200)
      (:body response)
      nil)))

(defn request-weather [latitude longitude time unit-system language]
  (execute-request (form-request-url latitude longitude time unit-system language)))

(defn string-to-json [string]
  (cheshire/parse-string string))

(defn form-head-item-response [item]
  (let [daily-data (first (-> item :daily :data))]
    {:latitude (:latitude item)
     :longitude (:longitude item)
     :timezone (:timezone item)
     :weather
      {:icon (-> item :currently :icon)
       :temperature (-> item :currently :temperature)
       :apparentTemperature (-> item :currently :apparentTemperature)
       :humidity (-> item :currently :humidity)
       :windSpeed (-> item :currently :windSpeed)
       :windBearing (-> item :currently :windBearing)
       :cloudCover (-> item :currently :cloudCover)}}))
    ;  :sun
      ; {:sunrise (:sunriseTime daily-data)
      ;  :sunset (:sunsetTime daily-data)}}))

(defn form-item-response [item]
  (string-to-json item))

(defn current-weather [latitude longitude unit-system language]
  (let [current-time (current-unix-timestamp)
        weather-data (request-weather latitude longitude current-time unit-system language)
        json (cheshire/parse-string weather-data true)]
      {:body
        {:current (form-head-item-response json)}}))
