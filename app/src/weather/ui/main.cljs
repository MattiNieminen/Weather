(ns ^:figwheel-always weather.ui.main
  (:require [weather.localization :refer [tr]]
            [reagent.core :as reagent]
            [weather.ui.view.controls :as controls]
            [weather.ui.view.weather :as weather]
            [weather.ui.state :as state]))

(defn main-view
  []
  [:div
   [:h1 (tr :title)]
   [controls/controls]
   [weather/weather-view @state/state]])

(defn init!
  []
  (.log js/console "Here we go! Rendering the components...")
  (state/init-hook!)
  (reagent/render [main-view] (js/document.getElementById "app")))

(init!)
