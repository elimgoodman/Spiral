(ns spiral.test.librarian
  (:use [spiral.librarian])
  (:use [spiral.test.data])
  (:use [clojure.test]))

(def test-librarian (in-memory-librarian))

(deftest store-methods
  (is [first-method] (do
                 (store-method test-librarian first-method)
                 (get-all-methods test-librarian))))