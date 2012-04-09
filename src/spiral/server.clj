(ns spiral.server
  (:use compojure.core)
  (:use clojure.data.json)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [spiral.librarian :as librarian]
            [spiral.test.data :as d]
            [spiral.records :as r]
            [spiral.views :as v]))

(def lib (librarian/in-memory-librarian))

(librarian/store-method lib d/first-method)
(librarian/store-type lib r/IntegerType)

(defn serialize [obj]
  (json-str obj))

(defn json-resp [body]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body body})

(defroutes main-routes
  (GET "/methods" [] (json-resp (serialize (librarian/get-all-methods lib))))
  (GET "/types" [] (json-resp (serialize (librarian/get-all-types lib))))
  (GET "/" [] (v/index-page))
  (route/resources "/")
  (route/not-found "Page not found"))
 
(def app
  (handler/site main-routes))
