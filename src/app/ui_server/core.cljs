(ns app.ui-server.core
  "Cliente UIx responsável pela hidratação dos componentes renderizados no servidor."
  (:require
   [app.ui-server.adapters.parse-page-data :refer [read-page-data]]
   [app.ui-server.components.footer :as components.footer]
   [app.ui-server.components.header :as components.header]
   [app.ui-server.routes :refer [routes]]
   [uix.core :as uix :refer [$ defui]]
   [uix.dom]))

(defui app []
  (let [page-data (read-page-data)
        page-type (-> page-data :page keyword)]
    ($ :div.app
       ($ components.header/header)
       ($ routes {:page-type page-type :page-data page-data})
       ($ components.footer/footer))))

(defonce ^:private root
  (uix.dom/create-root (js/document.getElementById "app")))

(defn ^:export init []
  (uix.dom/render-root ($ app) root))

(defn ^:export reload []
  (init))
