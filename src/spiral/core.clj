(ns spiral.core
  (:require [somnium.congomongo :as mongo]))

(def conn
  (mongo/make-connection "mydb"
                   :host "127.0.0.1"
                   :port 27017))

(mongo/set-connection! conn)

; TODO: add a "Persistable" protocol to these?
(defrecord Type [name fields is_primitive])
(defrecord Field [name type])
(defrecord Method [name return_type parameters statements])
(defrecord Parameter [name type])
(defrecord MethodReference [method_type value])
(defrecord Statement [method_ref args])
(defrecord Symbol [name useable_value])
(defrecord UseableValue [value_type value])

(def types-to-collections {Type :types
                           Method :methods
                           Parameter :parameters
                           Statement :statements
                           Symbol :symbols
                           UseableValue :useable-values})

(def spiral-types (keys types-to-collections))

(def all-collections (vals types-to-collections))

;Clear out all of the collections
(defn drop-all-colls []
  (map (fn [c] (mongo/drop-coll! c)) all-collections))

(drop-all-colls)

;Make some test data

;Primitives
(def IntegerType (Type. "Integer" [] true))
(def StringType (Type. "String" [] true))
(def ArrayType (Type. "Array" [] true))
(def MapType (Type. "Map" [] true))

(def NameType (Type. "Name" [(Field. "first" StringType) (Field. "last" StringType)] false))

(def primitives [IntegerType StringType ArrayType MapType])

(defn get-type-for-record [r]
  (first (filter (fn [t] (instance? t r)) spiral-types)))
  ;(types-to-collections (class r))) //FIXME: why doesn't this work?

(defn get-collection-for-record [r]
  (types-to-collections (get-type-for-record r)))

(defn insert-spiral-record [r]
  (mongo/insert! (get-collection-for-record r) r))

(def inserted
  (apply hash-map
         (flatten
          (map (fn [p] (list p (insert-spiral-record p))) primitives))))

; Insert a really simple function

(def integer-id (:_id (inserted IntegerType)))
(def param-1 (Parameter. "first" IntegerType))
(def param-2 (Parameter. "second" IntegerType))
(def stmt (Statement.
           (MethodReference. :literal "+")
           [(UseableValue. :literal "4") (UseableValue. :literal "3")]))
(def other-stmt (Statement.
           (MethodReference. :literal "+")
           [(UseableValue. :param param-1) (UseableValue. :param param-2)]))
;; (def first-method (Method. "simple-method" IntegerType [param-1 param-2] [stmt]))
(def first-method (Method. "simple-method" IntegerType [param-1 param-2] [other-stmt]))

;; (insert-spiral-record first-method)

(defn get-function-symbol [stmt]
  (eval (read-string (-> stmt :method_ref :value))))

(defn get-val-of-param [arg vals]
  (let [param-keyword (-> arg :value :name keyword)
        val (param-keyword vals)]
    (if (nil? val)
      (throw (Throwable. (str "Missing value for " param-keyword)))
      (read-string val))))

(defn get-arg-value [arg vals]
  (case (:value_type arg)
    :param (get-val-of-param arg vals)
    :literal (read-string (:value arg))))

(defn get-arg-values [stmt vals]
  (vec (map #(get-arg-value % vals) (:args stmt))))

(defn execute-statement [stmt vals]
  (apply (get-function-symbol stmt) (get-arg-values stmt vals))) 

(defn execute-method [method vals]
  (apply execute-statement (:statements method) vals))




(def p (UseableValue. :param param-1))





