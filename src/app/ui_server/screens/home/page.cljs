(ns app.ui-server.screens.home.page 
  (:require
   [app.ui-server.components.cta :as components.cta]
   [uix.core :refer [$ defui use-state]]))

(defui hero
  [{:keys [tagline title subtitle primary-cta secondary-cta]}]
  ($ :section.hero
     ($ :div.container
        ($ :p.hero-tagline tagline)
        ($ :h1.hero-title title)
        ($ :p.hero-subtitle subtitle)
        ($ :div.hero-actions
           (when primary-cta
             ($ components.cta/call-to-action (assoc primary-cta :variant :primary)))
           (when secondary-cta
             ($ components.cta/call-to-action (assoc secondary-cta :variant :secondary)))))))

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
  (let [[count set-count] (use-state 0)]
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
  [{:keys [hero-data feature-cards quick-links]}]
  ($ :<>
     ($ hero hero-data)
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
