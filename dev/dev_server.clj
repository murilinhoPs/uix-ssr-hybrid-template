(ns dev-server
  (:require [clojure.java.shell :as shell]))

(defn run-command
  "Executa um comando em uma thread separada"
  [name command-str]
  (future
    (println (str "[" name "] Starting..."))
    (let [result (shell/sh "sh" "-c" command-str)]
      (println (str "[" name "] " (:out result)))
      (when-not (zero? (:exit result))
        (println (str "[" name "] Error: " (:err result))))
      result)))

(defn -main
  [& _]
  (println "Starting server and client processes in parallel...")
  ;; Inicia shadow-cljs watch
  (let [deps-process (run-command "npm install" "npm install")
        _ (Thread/sleep 3000)
        watch-process (run-command "shadow-cljs watch" "npx shadow-cljs watch app")
        ;; Aguarda um pouco para o shadow-cljs inicializar
        _ (Thread/sleep 3000)
        ;; Inicia o servidor
        server-process (run-command "clj server" "clj -M -m app.server")]
    ;; Aguarda ambos os processos
    @server-process
    @deps-process
    @watch-process))

;; (defn run-command
;;   [name & args]
;;   (future
;;     (println (str "[" name "] Iniciando..."))
;;     (let [result (apply shell/sh args)]
;;       (println (str "[" name "] " (:out result)))
;;       result)))

;; ;; Use
;; (run-command "watch" "npx" "shadow-cljs" "watch" "app")