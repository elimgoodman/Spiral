(ns spiral.views.index
  (:use [hiccup core page-helpers]))

(defn js-var [name]
  (str "<%=" name "%>"))

(defn js-tmpl [id content]
  [:script {:type "text/html" :id id}
   content])

(defn type-selector []
   [:input.type-selector {:placeholder "Type..."}])

(defn new-type-field []
  (list
   [:input.field-name {:placeholder "Name"}]
   (type-selector)))

(defn type-input-form []
  [:form#type-form 
   [:ul
    [:li
     [:input {:name "type_name" :placeholder "Type name"}]]
    [:li
     [:ul#type-fields]
     [:a.add-field-link {:href "#"} "Add a field"]]
    [:li
     [:input#save-btn {:type "submit" :value "save"}]]]])

(defn index-page []
  (html5
   [:head
    [:title "HI"]
    (js-tmpl "type-field-tmpl" (new-type-field))
    (include-js "/js/jquery.js")
    (include-js "/js/underscore.js")
    (include-js "/js/backbone.js")
    (include-js "/js/index.js")]
   [:body
    [:h1 "WHATUP"]
    (type-input-form)
    [:h3 "All Types"]
    [:ul#all-types]]))