(ns app.ui-server.i18n
  (:require [taoensso.tempura :as tempura :refer [tr]]))

(def i18n-dictionary
  {:en   {:header         {:shuffle-deck "Title"
                           :subtitle "New subtitle"
                           :close "Close"}
          :modal          {:cancel "Cancel"
                           :ok "Confirm"
                           :heading "Action Required"
                           :example-options {:play "Play"
                                             :pause "Pause"
                                             :select-action "Select what you want to do:"}}
          :missing        "Missing translation"}
   :pt   {:header         {:title "Título"
                           :subtitle "Novo subtítulo"
                           :close "Fechar"}
          :modal          {:cancel "Cancelar"
                           :ok "Confirmar"
                           :heading "Atenção"
                           :example-options {:play "Jogar"
                                             :pause "Pause"
                                             :select-action "Escolha o que deseja fazer:"}}
          :missing        "Texto não existe"}})

(def check-lang
  (let [js-language (.-language js/navigator)]
    (if (nil? js-language) :en js-language)))

(def opts {:dict i18n-dictionary})

(def app-tr (partial tr opts [check-lang]))

;; (app.i18n/app-tr [:modal/heading])
;; (app.i18n/app-tr [:modal.example-options/play])]
