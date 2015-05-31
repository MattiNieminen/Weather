(ns ^:figwheel-always weather.ui.main
  (:require [weather.localization :refer [tr]]
            [reagent.core :as reagent]
            [weather.ui.view.navigation :as navigation]
            [weather.ui.view.view :as view]
            [weather.ui.view.views]
            [weather.ui.route :as route]))

(defn main-view
  []
  [:div
   [:h1 (tr :title)]
   [navigation/navigation]
   (view/render-view @route/route)])

(defn init!
  []
  (.log js/console "Here we go! Rendering the components...")
  (route/init-hook!)
  (reagent/render [main-view] (js/document.getElementById "app")))

(init!)
