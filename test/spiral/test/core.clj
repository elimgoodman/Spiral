(ns spiral.test.core
  (:use [spiral.core])
  (:use [clojure.test]))

(deftest execute-stmt-simple
  (is 7 (execute-statement stmt {})))

(deftest execute-stmt-param
  (is 9 (execute-statement other-stmt {:first 4 :second 5})))

(deftest execute-stmt-nested
  (is 21 (execute-statement nested-stmt {:second 3})))
