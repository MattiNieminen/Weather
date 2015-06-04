(ns weather.ui.view.current
  (:require [reagent.core :as reagent]
            [weather.localization :refer [tr]]
            [weather.ui.utils :as utils]))

(defn weather-data-view
  [current-weather-data]
  [:div
   [:h2 (tr :current-weather)]
   [:p#temp_c (utils/->celsius-str (:temp_c current-weather-data))]
   [:p#weather-text (:weather current-weather-data)]])

(defn weather-view
  [data]
  (cond (nil? data)
        [:span (tr :fetching-data)]
        
        (= 200 (:status data))
        [weather-data-view (:body data)]))
