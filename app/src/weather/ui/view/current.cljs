(ns weather.ui.view.current
  (:require [reagent.core :as reagent]
            [weather.localization :refer [tr]]
            [weather.ui.utils :as utils]))

(defn weather-data-view
  [current-weather-data]
  [:div
   [:h2 (tr :current-weather)]
   [:p (:temp_c current-weather-data)]
   [:p (:weather current-weather-data)]])

(defn weather-view
  [state]
  (cond (nil? (:current-weather-data state))
        [:span "Fetching data..."]
        
        (= 200 (:status (:current-weather-data state)))
        [weather-data-view (:body (:current-weather-data state))]))
