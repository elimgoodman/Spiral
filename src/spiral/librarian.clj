(ns spiral.librarian)

(defprotocol Librarian
  (store-method [this method])
  (store-type [this type])
  (get-all-types [this])
  (get-all-methods [this]))

(defrecord InMemoryLibrarian [methods types]
  Librarian
  (store-method [this method]
    (dosync (alter (:methods this) conj method)))
  (store-type [this type]
    (dosync (alter (:types this) conj types)))
  (get-all-types [this]
    (deref (:types this)))
  (get-all-methods [this]
    (deref (:methods this))))

(defn in-memory-librarian []
  (InMemoryLibrarian. (ref []) (ref [])))