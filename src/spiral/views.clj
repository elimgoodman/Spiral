(ns spiral.views
  (:use [hiccup core page-helpers]))

(defn js-var [name]
  (str "<%=" name "%>"))

(defn id-ify [string]
  (str "#" string))

(defn js-tmpl [id content]
  [:script {:type "text/html" :id (id-ify id)}
   content])

(defn type-selector []
   [:input.type-selector {:placeholder "Type..."}])

(defn new-type-field []
  [:li
   (type-selector)
   [:input.field-name]])

(defn type-input-form []
  [:form 
   [:ul
    [:li
     [:input {:name "type_name" :placeholder "Type name"}]]
    [:li
     [:ul#type-fields]
     [:a.add-field-link {:href "#"} "Add a field"]]]])

(defn index-page []
  (html5
   [:head
    [:title "HI"]
    (include-js "/js/jquery.js")
    (include-js "/js/underscore.js")
    (include-js "/js/backbone.js")
    (include-js "/js/index.js")]
   [:body
    [:h1 "WHATUP"]
    (js-tmpl "type-field-tmpl" (new-type-field))
    (type-input-form)]))