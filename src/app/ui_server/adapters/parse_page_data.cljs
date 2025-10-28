(ns app.ui-server.adapters.parse-page-data)

;; ----------------------------------------------------------------------------- 
;; Leitura dos dados do servidor
;; -----------------------------------------------------------------------------

(defn read-page-data []
  (when-let [el (.getElementById js/document "page-data")]
    (js->clj (.parse js/JSON (.-textContent el)) :keywordize-keys true)))

