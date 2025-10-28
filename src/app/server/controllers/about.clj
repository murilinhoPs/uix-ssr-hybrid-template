(ns app.server.controllers.about 
  (:require
   [app.server.logic.page-metadata :refer [build-page-metadata]]
   [app.server.helpers.render :refer [render-shell]]))

(defn about-route-handler [_request]
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
