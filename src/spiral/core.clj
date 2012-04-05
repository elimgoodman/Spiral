(ns spiral.core
  (:require [spiral.records :as rec]))

(defn get-function-symbol [stmt]
  (eval (-> stmt :method_ref :value)))

(defn get-val-of-param [arg vals]
  (let [param-keyword (:symbol arg)
        val (param-keyword vals)]
    (if (nil? val)
      (throw (Throwable. (str "Missing value for " param-keyword)))
      val)))

(declare get-arg-value execute-statement)

(defmulti get-arg-value (fn [arg vals] (class arg)))

;; (defmethod get-arg-value rec/Literal [arg vals]
;;   (:value arg))

;; (defmethod get-arg-value rec/Parameter [arg vals]
;;     (get-val-of-param arg vals))

;; (defmethod get-arg-value rec/Statement [arg vals]
;;     (execute-statement arg vals))

;; (defn get-arg-values [stmt vals]
;;   (vec (map #(get-arg-value % vals) (:args stmt))))

;; (defn execute-statement [stmt vals]
;;   (apply (get-function-symbol stmt) (get-arg-values stmt vals))) 

;; (defn execute-method [method vals]
;;   (apply execute-statement (:statements method) vals))
