(ns spiral.server
  (:use compojure.core)
  (:use clojure.data.json)
  (:use ring.middleware.json-params)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [spiral.librarian :as librarian]
            [spiral.test.data :as d]
            [spiral.records :as r]
            [spiral.views :as v]))

(def lib (librarian/in-memory-librarian))

(doseq [p r/primitives] (librarian/store-type lib p))
(librarian/store-method lib d/first-method)

(defn serialize [obj]
  (json-str obj))

(defn json-resp [body]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body body})

(defn fields-from-type-map [type-map]
  "blerg")

(defn insert-type-from-map [type-map]
  (let [type-map (assoc type-map :fields (fields-from-type-map type-map))
        type-rec (merge (r/->Type nil nil false) type-map)]
    (println type-rec)))
        
(defroutes main-routes
  (GET "/methods" [] (json-resp (serialize (librarian/get-all-methods lib))))
  (GET "/types" [] (json-resp (serialize (librarian/get-all-types lib))))
  (POST "/type" request (let [j (:json-params request)]
                          (insert-type-from-map j)))
  (GET "/" [] (v/index-page))
  (route/resources "/")
  (route/not-found "Page not found"))
 
(def app
  (-> main-routes handler/site wrap-json-params))
  