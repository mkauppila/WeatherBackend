(ns weather.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]
            [weather.weather :as weather]))

(defroutes app-routes
  (GET "/api/current" [latitude longitude unit-system language]
    (weather/current-weather latitude longitude unit-system language))
  (route/not-found
    "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)))

(defn -main [& args]
  (let [port (Integer. (or (System/getenv "PORT") 3000))]
    (jetty/run-jetty app {:port port})))
