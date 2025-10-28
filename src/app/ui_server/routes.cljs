(ns app.ui-server.routes
  (:require
   [app.ui-server.screens.home.page :as screens.home.page]
   [app.ui-server.screens.about.page :as screens.about.page]
   [app.ui-server.screens.not-found.page :as screens.not-found.page]
   [uix.core :refer [$ defui]]))

(defui routes
  [{:keys [page-type page-data]}]
  ($ :main
     (case page-type
       :home ($ screens.home.page/home-page page-data)
       :about ($ screens.about.page/about-page page-data)
       ($ screens.not-found.page/not-found-page))))
