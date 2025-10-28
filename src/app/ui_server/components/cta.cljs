(ns app.ui-server.components.cta 
  (:require
   [uix.core :refer [$ defui]]))

(defui call-to-action
  [{:keys [label href variant]}]
  (let [class (str "btn " (case variant
                            :primary "btn-primary"
                            :secondary "btn-secondary"
                            ""))]
    ($ :a {:class class :href href} label)))
