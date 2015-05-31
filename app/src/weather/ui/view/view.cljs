(ns weather.ui.view.view
  (:require [weather.localization :refer [tr]]))

(defmulti render-view :id)

(defmethod render-view :default []
  [:div#content
   [:h2 (tr :not-found)]])
