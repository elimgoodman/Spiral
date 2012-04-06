(ns spiral.rest-api
  (:use compojure.core)
  (:use clojure.data.json)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [spiral.librarian :as librarian]
            [spiral.test.data :as d]
            [spiral.records :as r]))

(def lib (librarian/in-memory-librarian))

(librarian/store-method lib d/first-method)
(librarian/store-type lib r/IntegerType)

(defn serialize [obj]
  (json-str obj))

(defroutes main-routes
  (GET "/methods" [] (serialize (librarian/get-all-methods lib)))
  (GET "/types" [] (serialize (librarian/get-all-types lib)))
  (route/resources "/")
  (route/not-found "Page not found"))
 
(def app
  (handler/site main-routes))
