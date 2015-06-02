(ns weather.ui.view.history
  (:require [reagent.core :as reagent]
            [weather.localization :refer [tr]]
            [weather.ui.utils :as utils]))

(defn weather-data-view
  [weather-data]
  [:div
   [:h2 (tr :data-arrived)]
   [:ul
    [:li (str (tr :highest) (:maxtempm weather-data))]
    [:li (str (tr :lowest) (:mintempm weather-data))]
    (if (> (:fog weather-data) 0) [:li (tr :fog)])
    (if (> (:rain weather-data) 0) [:li (tr :rain)] [:h1])
    (if (> (:snow weather-data) 0) [:li (tr :snow)])
    (if (> (:hail weather-data) 0) [:li (tr :hail)])]])

(defn error-view
  []
  [:div
   [:h1 (tr :bad-request)]])

(defn weather-view
  [state]
  (cond (nil? (:weather-data state))
        [:span "Fetching data..."]
        
        (= 200 (:status (:weather-data state)))
        [weather-data-view (:body (:weather-data state))]
        
        (not (:status (:weather-data state)))
        [error-view]))
