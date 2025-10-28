(ns app.server.helpers.render 
  (:require
   [cheshire.core :as json]
   [hiccup2.core :as h]))

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
