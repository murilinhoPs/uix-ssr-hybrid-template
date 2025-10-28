(ns app.ui-server.components.footer
  (:require
   [uix.core :refer [$ defui]]))

(defui footer []
  ($ :footer.site-footer
     ($ :div.container
        ($ :span "UIx SSR Template — pronto para personalizar"))))
