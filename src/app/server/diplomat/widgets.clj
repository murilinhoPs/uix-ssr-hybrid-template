(ns app.server.diplomat.widgets 
  (:require
   [app.server.controllers.about :refer [about-route-handler]]
   [app.server.controllers.home :refer [home-route-handler]]
   [compojure.core :refer [defroutes GET]]))

(defroutes widgets
  (GET "/" [] home-route-handler)
  (GET "/about" [] about-route-handler))
