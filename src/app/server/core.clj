(ns app.server.core
  "Servidor Ring/Http-kit com SSR básico pronto para hidratação UIx."
  (:require
   [app.server.diplomat.http-server :refer [app-routes]]
   [app.server.helpers.logging :refer [wrap-request-logging]]
   [app.server.state :as state]
   [org.httpkit.server :refer [run-server]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.reload :refer [wrap-reload]]))

(def app
  (-> app-routes
      wrap-params
      wrap-reload
      wrap-request-logging))

(defn -main
  [& _args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println (format "🚀 Servidor disponível em http://localhost:%s" port))
    (reset! state/server (run-server app {:port port}))))

(comment
  (state/reset-server)
  (state/stop-server)
  @state/server
  (app {:request-method :get
        :uri "/api"}))
