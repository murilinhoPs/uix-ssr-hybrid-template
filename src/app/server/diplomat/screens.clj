(ns app.server.diplomat.screens 
  (:require
   [app.server.controllers.about :refer [about-route-handler]]
   [app.server.controllers.home :refer [home-route-handler]]
   [compojure.core :refer [defroutes GET]]))

(defroutes screens
  (GET "/" [] home-route-handler)
  (GET "/about" [] about-route-handler))
