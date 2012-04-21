(ns spiral.views.poc
  (:use [hiccup core page-helpers])
  (:use [cssgen]))

(def font-path
  "http://fonts.googleapis.com/css?family=Ubuntu+Mono:400,700,400italic,700italic")


(defn js-var [name]
  (str "<%=" name "%>"))

(defn js-tmpl [id content]
  [:script {:type "text/html" :id id}
   content])

(defn model-li []
  (js-var "name"))

(defn model-list []
  [:div#model-list-container.spiral-obj-container
   [:div.block-header "Models"]
   [:ul#model-list]])

(defn model-details []
   [:div.block-header (js-var "name")])

(defn index-page []
  (html5
   [:head
    (include-css "/css/poc.css")
    (include-css font-path)
    (include-js "/js/jquery.js")
    (include-js "/js/underscore.js")
    (include-js "/js/backbone.js")
    (include-js "/js/poc.js")]
   [:body
    [:div#content 
     (model-list)
     [:div#model-details-container.spiral-obj-container]
     (js-tmpl "model-li-tmpl" (model-li))
     (js-tmpl "model-details-tmpl" (model-details))]]))
