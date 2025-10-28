(ns app.ui-server.screens.not-found.page
  (:require
   [app.ui-server.components.cta :as components.cta]
   [uix.core :refer [$ defui]]))

(defui not-found-page []
  ($ :div.not-found
     ($ :div.container
        ($ :h1 "404")
        ($ :p "Conteúdo não encontrado.")
        ($ components.cta/call-to-action {:href "/" :label "Voltar para home" :variant :primary}))))
