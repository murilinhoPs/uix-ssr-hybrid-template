(ns app.server.controllers.home 
  (:require
   [app.server.helpers.render :refer [render-shell]]
   [app.server.logic.page-metadata :refer [build-page-metadata]]))

(defn home-route-handler [_request]
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
