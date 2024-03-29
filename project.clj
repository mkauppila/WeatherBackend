(defproject weather "0.1.0-SNAPSHOT"
  :description "Proxy backend for requesting weather info from Darksky.net"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [http-kit "2.1.16"]
                 [cheshire "5.6.3"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler weather.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
