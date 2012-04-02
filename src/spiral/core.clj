(ns spiral.core
  (:require [somnium.congomongo :as mongo]))

(def conn
  (mongo/make-connection "mydb"
                   :host "127.0.0.1"
                   :port 27017))

(mongo/set-connection! conn)

(defrecord Type [name fields is_primitive])
(defrecord Method [name return_type parameters statements])
(defrecord Parameter [name type_id])
(defrecord Statement [method args])
(defrecord Symbol [name useable_value])
(defrecord UseableValue [value_type value])

(def types-to-collections {Type :types
                           Method :methods
                           Parameter :parameters
                           Statement :statements
                           Symbol :symbols
                           UseableValue :useable-values})

(def spiral-types (keys types-to-collections))

(defn all-collections []
  (vals types-to-collections))

;Clear out all of the collections
(defn drop-all-colls []
  (map (fn [c] (mongo/drop-coll! c)) (all-collections)))

(drop-all-colls)

;Make some test data

;Primitives
(def IntegerType (Type. "Integer" [] true))
(def StringType (Type. "String" [] true))
(def ArrayType (Type. "Array" [] true))
(def MapType (Type. "Map" [] true))

(def primitives [IntegerType StringType ArrayType MapType])

(defn get-type-for-record [r]
  (first (filter (fn [t] (instance? t r)) spiral-types)))

(defn get-collection-for-record [r]
  (types-to-collections (get-type-for-record r)))

(defn insert-spiral-record [r]
  (mongo/insert! (get-collection-for-record r) r))

(map insert-spiral-record primitives)