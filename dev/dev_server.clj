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
        server-process (run-command "clj server" "clj -M -m app.server")]
    ;; Aguarda ambos os processos
    @watch-process
    @server-process))

;; ProcessBuilder passo a passo

;; Criar o comando – ProcessBuilder. recebe uma lista de strings com o executável e seus argumentos. Aqui vira ["sh" "-c" command-str], que usa o shell para interpretar toda a string (command-str) exatamente como você rodaria no terminal.

;; Unir stdout e stderr – .redirectErrorStream true faz com que tudo que sair por stderr seja redirecionado para stdout. Assim não precisamos ler dois streams separados; tudo vai para o mesmo lugar.

;; Iniciar o processo – .start dispara o comando em background e devolve um Process. Ele não bloqueia; o código segue adiante enquanto o comando roda.

;; Ler a saída ao vivo – (.getInputStream proc) dá acesso ao stream de saída padrão do processo. Usamos io/reader para transformar em um reader Clojure, e line-seq para iterar linha a linha. O with-open garante que o stream seja fechado quando acabarmos.

;; Logar cada linha – Dentro do doseq, imprimimos cada linha prefixada com o nome do serviço, mantendo os logs organizados.

;; Esperar o fim – .waitFor bloqueia até o processo encerrar e devolve o código de saída. Como estamos dentro do future, isso não trava a thread principal; apenas evita que o future finalize antes do processo.

;; No conjunto, essa função dispara o comando, mantém o log fluindo em tempo real e só termina quando o processo realmente parar.
