(ns weather.weather
  (:require [org.httpkit.client :as http]))

(defn env [name]
  (or (System/getenv name) "no-default"))

(defn base-url-with-token []
  (str (env "DARKSKY_URL") "/" (env "DARKSKY_TOKEN")))

(defn coordinate [latitude longitude]
  (str latitude "," longitude))

(defn exclude-sections [blocks]
  (str "exclude=" (clojure.string/join "," blocks)))

(defn request-current-weather [latitude longitude]
  (http/get (str
    (base-url-with-token)
    "/"
    (coordinate latitude longitude)
    "?"
    (exclude-sections ["minutely" "hourly" "daily" "alerts" "flags"])
    "&"
    "units=si"
    "&"
    "language=en")))

(defn current-weather [latitude longitude]
  (let [req @(request-current-weather latitude longitude)]
    {:body (:body req)}))
