(ns app.client
  "Cliente UIx responsável pela hidratação dos componentes renderizados no servidor."
  (:require [uix.core :as uix :refer [$ defui]]
            [uix.dom]))

;; ----------------------------------------------------------------------------- 
;; Leitura dos dados do servidor
;; -----------------------------------------------------------------------------

(defn read-page-data []
  (when-let [el (.getElementById js/document "page-data")]
    (js->clj (.parse js/JSON (.-textContent el)) :keywordize-keys true)))

;; ----------------------------------------------------------------------------- 
;; Componentes compartilhados
;; -----------------------------------------------------------------------------

(defui header []
  ($ :header.site-header
     ($ :div.container
        ($ :div.brand
           ($ :a {:href "/"} "UIx SSR Template"))
        ($ :nav
           ($ :a {:href "/"} "Home")
           ($ :a {:href "/about"} "Sobre")))))

(defui footer []
  ($ :footer.site-footer
     ($ :div.container
        ($ :span "UIx SSR Template — pronto para personalizar"))))

(defui call-to-action
  [{:keys [label href variant]}]
  (let [class (str "btn " (case variant
                            :primary "btn-primary"
                            :secondary "btn-secondary"
                            ""))]
    ($ :a {:class class :href href} label)))

(defui hero
  [{:keys [tagline title subtitle primary-cta secondary-cta]}]
  ($ :section.hero
     ($ :div.container
        ($ :p.hero-tagline tagline)
        ($ :h1.hero-title title)
        ($ :p.hero-subtitle subtitle)
        ($ :div.hero-actions
           (when primary-cta
             ($ call-to-action (assoc primary-cta :variant :primary)))
           (when secondary-cta
             ($ call-to-action (assoc secondary-cta :variant :secondary)))))))

(defui feature-card
  [{:keys [title description]}]
  ($ :li.feature-card
     ($ :h3 title)
     ($ :p description)))

(defui quick-link
  [{:keys [label note]}]
  ($ :li.quick-link
     ($ :strong label)
     ($ :span note)))

(defui counter-example []
  (let [[count set-count] (uix/use-state 0)]
    ($ :div.counter-example
       ($ :p "Componente interativo de exemplo")
       ($ :div.counter-display count)
       ($ :div.counter-actions
          ($ :button {:on-click #(set-count dec)} "-1")
          ($ :button {:on-click #(set-count (constantly 0))} "Reset")
          ($ :button {:on-click #(set-count inc)} "+1")))))

;; ----------------------------------------------------------------------------- 
;; Páginas
;; -----------------------------------------------------------------------------

(defui home-page
  [{:keys [hero feature-cards quick-links]}]
  ($ :<>
     ($ hero hero)
     ($ :section#componentes.section
        ($ :div.container
           ($ :h2.section-title "Blocos prontos para editar")
           ($ :ul.feature-grid
              (for [{:keys [title] :as card} feature-cards]
                ($ feature-card (assoc card :key title))))
           ($ counter-example)))
     ($ :section.section.section-muted
        ($ :div.container
           ($ :h2.section-title "Checklist de personalização")
           ($ :ul.quick-links
              (for [{:keys [label] :as link} quick-links]
                ($ quick-link (assoc link :key label))))))))

(defui about-section
  [{:keys [title items paragraphs]}]
  ($ :section.section
     ($ :div.container
        ($ :h2.section-title title)
        (when (seq paragraphs)
          (for [paragraph paragraphs]
            ($ :p {:key paragraph} paragraph)))
        (when (seq items)
          ($ :ul
             (for [item items]
               ($ :li {:key item} item)))))))

(defui about-page
  [{:keys [sections]}]
  ($ :div.about-page
     (for [{:keys [title] :as section} sections]
       ($ about-section (assoc section :key title)))))

(defui not-found-page []
  ($ :div.not-found
     ($ :div.container
        ($ :h1 "404")
        ($ :p "Conteúdo não encontrado.")
        ($ call-to-action {:href "/" :label "Voltar para home" :variant :primary}))))

;; ----------------------------------------------------------------------------- 
;; Componente raiz
;; -----------------------------------------------------------------------------

(defui app []
  (let [page-data (read-page-data)
        page-type (-> page-data :page keyword)]
    ($ :div.app
       ($ header)
       ($ :main
          (case page-type
            :home ($ home-page page-data)
            :about ($ about-page page-data)
            ($ not-found-page)))
       ($ footer))))

(defonce ^:private root
  (uix.dom/create-root (js/document.getElementById "app")))

(defn ^:export init []
  (uix.dom/render-root ($ app) root))

(defn ^:export reload []
  (init))
