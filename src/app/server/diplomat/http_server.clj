(ns app.server.diplomat.http-server
  (:require
   [app.server.config :as config]
   [app.server.diplomat.widgets :as widgets]
   [app.server.helpers.render :refer [render-shell]]
   [app.server.logic.page-metadata :refer [build-page-metadata]]
   [cheshire.core :as json]
   [compojure.core :refer [defroutes GET routes]]
   [compojure.route :as route]))

(defroutes app-routes
  (routes
   widgets/widgets
   (GET "/api" [] (json/encode {:status 200 :body (str "API version: " config/api-version)}))
   (route/resources "/")
   (route/not-found
    (fn [_]
      {:status 404
       :headers {"Content-Type" "text/html; charset=utf-8"}
       :body (render-shell
              (build-page-metadata {:title "404 - Página não encontrada" :url "/404"})
              {:page :not-found})}))))
