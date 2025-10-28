(ns app.server.state 
  (:require
   [app.server.core :refer [-main]]))

;; ----------------------------------------------------------------------------- 
;; Server interativo com REPL
;; -----------------------------------------------------------------------------

(defonce server (atom nil))

(defn stop-server []
  (when @server
    (@server :timeout 100)
    (reset! server nil)))

(defn reset-server []
  (stop-server)
  (-main))
