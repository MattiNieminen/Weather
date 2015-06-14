(ns weather.ui.view.history
  (:require [reagent.core :as reagent]
            [weather.localization :refer [tr]]
            [weather.ui.utils :as utils]))

(defn weather-data-view
  [weather-history-data]
  [:div
   [:h2 (tr :data-arrived)]
   [:ul
    [:li (tr :highest)
              [:strong (utils/->celsius-str (:maxtempm weather-history-data))]]
    [:li (tr :lowest)
     [:strong (utils/->celsius-str (:mintempm weather-history-data))]]
    (if (> (:fog weather-history-data) 0) [:li (tr :fog)])
    (if (> (:rain weather-history-data) 0) [:li (tr :rain)])
    (if (> (:snow weather-history-data) 0) [:li (tr :snow)])
    (if (> (:hail weather-history-data) 0) [:li (tr :hail)])]])

(defn error-view
  []
  [:div
   [:p (tr :bad-request)]])

(defn weather-view
  [data]
  (cond (nil? data)
        [:span (tr :fetching-data)]
        
        (= 200 (:status data))
        [weather-data-view (:body data)]
        
        (not (= 200 (:status data)))
        [error-view]))
