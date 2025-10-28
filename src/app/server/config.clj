(ns app.server.config)

;; ----------------------------------------------------------------------------- 
;; Dados simples que podem ser trocados posteriormente
;; -----------------------------------------------------------------------------
(def api-version "v1")

(def site-info
  {:name "UIx SSR Template"
   :description "Template minimalista de SSR híbrido com Clojure e UIx."
   :base-url (or (System/getenv "APP_URL") "http://localhost:3000")})
