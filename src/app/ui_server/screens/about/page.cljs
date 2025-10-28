(ns app.ui-server.screens.about.page
  (:require
   [uix.core :refer [$ defui]]))

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
