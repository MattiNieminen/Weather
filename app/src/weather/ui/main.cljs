(ns ^:figwheel-always weather.ui.main
  (:require [weather.localization :refer [tr]]
            [reagent.core :as reagent]
            [weather.ui.view.controls :as controls]
            [weather.ui.route :as route]))

(defn main-view
  []
  [:div
   [:h1 (tr :title)]
   [controls/controls]])

(defn init!
  []
  (.log js/console "Here we go! Rendering the components...")
  (route/init-hook!)
  (reagent/render [main-view] (js/document.getElementById "app")))

(init!)
