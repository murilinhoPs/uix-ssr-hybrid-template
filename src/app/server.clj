(ns app.server
  "Servidor Ring/Jetty com SSR básico pronto para hidratação UIx."
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [hiccup2.core :as h]
            [cheshire.core :as json]))

;; ----------------------------------------------------------------------------- 
;; Dados simples que podem ser trocados posteriormente
;; -----------------------------------------------------------------------------

(def site-info
  {:name "UIx SSR Template"
   :description "Template minimalista de SSR híbrido com Clojure e UIx."
   :base-url (or (System/getenv "APP_URL") "http://localhost:3000")})

;; ----------------------------------------------------------------------------- 
;; Funções utilitárias
;; -----------------------------------------------------------------------------

(defn build-page-metadata
  "Cria metadados SEO básicos para a página."
  [{:keys [title description path]}]
  (let [full-title (str title " · " (:name site-info))
        page-url (str (:base-url site-info) path)
        meta-description (or description (:description site-info))]
    {:title full-title
     :description meta-description
     :canonical page-url}))

(defn render-meta-tags
  [{:keys [title description canonical]}]
  [[:meta {:charset "utf-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:title title]
   [:meta {:name "description" :content description}]
   [:link {:rel "canonical" :href canonical}]])

(defn render-shell
  "Monta o HTML e injeta os dados da página como JSON."
  [metadata page-data]
  (-> (h/html
       (into
        [:head]
        (concat
         (render-meta-tags metadata)
         [[:link {:rel "stylesheet" :href "css/main.css" :media "print" :onload "this.media='all'"}]]))
       [:body
        [:span {:class "sr-only"} "rendered on server"]
        [:div#app]
        [:script {:id "page-data" :type "application/json"}
         (-> (json/generate-string page-data {:pretty true}) h/raw)]
        [:script {:src "js/main.js" :defer true}]])
      str))

;; ----------------------------------------------------------------------------- 
;; Rotas
;; -----------------------------------------------------------------------------

(defn home-handler [_request]
  (let [metadata (build-page-metadata
                  {:title "Página Inicial"
                   :description "Ponto de partida simples para projetos com SSR híbrido."
                   :path "/"})
        page-data {:page :home
                   :hero {:tagline "SSR híbrido em minutos"
                          :title "Template pronto para customização"
                          :subtitle "Substitua este conteúdo por algo real e mantenha o fluxo SSR + hidratação."
                          :primary-cta {:label "Explorar componentes" :href "#componentes"}
                          :secondary-cta {:label "Ver documentação" :href "/about"}}
                   :feature-cards [{:title "Pré-configurado"
                                    :description "Servidor Ring, rotas Compojure e build Shadow-CLJS em funcionamento."}
                                   {:title "SEO básico"
                                    :description "Estrutura já inclui tags essenciais e injeção de dados."}
                                   {:title "UIx pronta"
                                    :description "Componentes simples e um contador de exemplo prontos para hidratação."}]
                   :quick-links [{:label "Alterar copy do hero" :note "Atualize os dados no handler `home-handler`."}
                                 {:label "Criar componentes novos" :note "Use UIx (React) no arquivo client.cljs."}
                                 {:label "Adicionar rotas adicionais" :note "Defina novas rotas no router Compojure."}]}]
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (render-shell metadata page-data)}))

(defn about-handler [_request]
  (let [metadata (build-page-metadata
                  {:title "Sobre o Template"
                   :description "Resumo das peças principais do template de SSR híbrido."
                   :path "/about"})
        page-data {:page :about
                   :sections [{:title "Stack inclusa"
                               :items ["Clojure (Ring + Compojure)"
                                       "Hiccup 2 para gerar HTML"
                                       "UIx (React) para hidratação"
                                       "Shadow-CLJS para build do front"]}
                              {:title "Fluxo sugerido"
                               :paragraphs ["1. Ajuste os dados no servidor para refletir suas páginas."
                                            "2. Edite os componentes UIx para aplicar estilos e interações."
                                            "3. Rode `npm run dev` para ver o servidor e o watcher em ação."]}
                              {:title "Próximos passos"
                               :items ["Substitua as seções exemplo por conteúdo real."
                                       "Adicione rotas ou endpoints de dados."
                                       "Integre serviços externos, auth ou CMS."]}]}]
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (render-shell metadata page-data)}))

(defroutes app-routes
  (GET "/" [] home-handler)
  (GET "/about" [] about-handler)
  (route/resources "/")
  (route/not-found
   (fn [_]
     {:status 404
      :headers {"Content-Type" "text/html; charset=utf-8"}
      :body (render-shell
             (build-page-metadata {:title "404 - Página não encontrada" :url "/404"})
             {:page :not-found})})))

(def app
  (-> app-routes
      wrap-params
      wrap-reload))

;; ----------------------------------------------------------------------------- 
;; Entry point
;; -----------------------------------------------------------------------------

(defn -main
  [& _args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println (format "Servidor disponível em http://localhost:%s" port))
    (run-jetty app {:port port :join? true})))
