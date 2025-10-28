(ns app.server.logic.page-metadata 
  (:require
   [app.server.config :refer [site-info]]))

(defn build-page-metadata
  "Cria metadados SEO básicos para a página."
  [{:keys [title description path]}]
  (let [full-title (str title " · " (:name site-info))
        page-url (str (:base-url site-info) path)
        meta-description (or description (:description site-info))]
    {:title full-title
     :description meta-description
     :canonical page-url}))
