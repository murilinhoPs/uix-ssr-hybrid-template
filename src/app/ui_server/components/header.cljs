(ns app.ui-server.components.header 
  (:require
   [uix.core :refer [$ defui]]))

(defui header []
  ($ :header.site-header
     ($ :div.container
        ($ :div.brand
           ($ :a {:href "/"} "UIx SSR Template"))
        ($ :nav
           ($ :a {:href "/"} "Home")
           ($ :a {:href "/about"} "Sobre")))))
