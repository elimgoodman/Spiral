(defproject spiral "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [congomongo "0.1.8"]
                 [compojure "1.0.1"]
                 [matchure "0.10.1"]]
  :dev-dependencies [[lein-ring "0.5.4"]]
  :ring {:handler spiral.rest-api/app})