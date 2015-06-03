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
    (if (> (:rain weather-history-data) 0) [:li (tr :rain)] [:h1])
    (if (> (:snow weather-history-data) 0) [:li (tr :snow)])
    (if (> (:hail weather-history-data) 0) [:li (tr :hail)])]])

(defn error-view
  []
  [:div
   [:h1 (tr :bad-request)]])

(defn weather-view
  [state]
  (cond (nil? (:weather-history-data state))
        [:span (tr :fetching-data)]
        
        (= 200 (:status (:weather-history-data state)))
        [weather-data-view (:body (:weather-history-data state))]
        
        (not (:status (:weather-history-data state)))
        [error-view]))
