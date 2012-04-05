(ns spiral.test.data
  (:require [spiral.records :as r])
  (:import [spiral.records Literal Parameter Statement MethodReference Method]))

; Insert a really simple function
(def param-1 (Parameter. :first r/IntegerType))
(def param-2 (Parameter. :second r/IntegerType))

(def stmt (Statement.
           (MethodReference. :literal +)
           [(Literal. 4) (Literal. 3)]))

(def other-stmt (Statement.
           (MethodReference. :literal +)
           [param-1 param-2]))

(def nested-stmt (Statement.
           (MethodReference. :literal *)
           [stmt param-2]))
;; (def first-method (Method. "simple-method" IntegerType [param-1 param-2] [stmt]))
(def first-method (Method. "simple-method" r/IntegerType [param-1 param-2] [other-stmt]))