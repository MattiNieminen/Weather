(ns ^:figwheel-always weather.ui.main
  (:require [weather.localization :refer [tr]]
            [reagent.core :as reagent]
            [weather.ui.view.controls :as controls]
            [weather.ui.view.current :as current]
            [weather.ui.view.history :as history]
            [weather.ui.state :as state]))

(defn main-view
  []
  [:div#content
   [:h1 (tr :title)]
   [controls/controls]
   (if (contains? @state/state :current-weather-data)
     [current/weather-view @state/state]
     [history/weather-view @state/state])])

(defn init!
  []
  (.log js/console "Here we go! Rendering the components...")
  (state/init-hook!)
  (reagent/render [main-view] (js/document.getElementById "app")))

(init!)
