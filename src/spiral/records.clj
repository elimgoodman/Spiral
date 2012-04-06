(ns spiral.records
  (:use defrecord2.defrecord2-core))

; TODO: add a "Persistable" protocol to these?
(defrecord Type [name fields is_primitive])
(defrecord Field [name type])
(defrecord Method [name return_type parameters statements])
(defrecord Parameter [symbol type])
(defrecord MethodReference [method_type value])
(defrecord Statement [method_ref args])
(defrecord Symbol [name useable_value])
(defrecord Literal [value])

;Make some test data

;Primitives
(def IntegerType (Type. "Integer" [] true))
(def StringType (Type. "String" [] true))
(def ArrayType (Type. "Array" [] true))
(def MapType (Type. "Map" [] true))

(def NameType (Type. "Name" [(Field. "first" StringType) (Field. "last" StringType)] false))

(def primitives [IntegerType StringType ArrayType MapType])