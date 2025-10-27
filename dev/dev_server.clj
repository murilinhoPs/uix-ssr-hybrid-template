(ns dev-server
  (:require [clojure.java.io :as io]))

(defn run-command [name command-str]
  (future
    (println (str "[" name "] Starting..."))
    (let [builder (doto (ProcessBuilder. (into-array String ["sh" "-c" command-str]))
                    (.redirectErrorStream true))
          process (.start builder)]
      (with-open [reader (io/reader (.getInputStream process))]
        (doseq [line (line-seq reader)]
          (println (str "[" name "] " line))))
      (.waitFor process))))

(defn -main
  [& _]
  (println "Starting server and client processes in parallel...")
  ;; Inicia shadow-cljs watch
  (let [watch-process (run-command "shadow-cljs watch" "npx shadow-cljs watch app")
        _ (Thread/sleep 3000)
        ;; Inicia o servidor
        server-process (run-command "clj server" "clj -M -m app.server.init")]
    ;; Aguarda ambos os processos
    @watch-process
    @server-process))
