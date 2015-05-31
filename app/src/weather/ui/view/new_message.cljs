(ns weather.ui.view.new-message
  (:require [reagent.core :as reagent]
            [weather.ui.utils :as utils]
            [weather.ui.route :as route]
            [weather.localization :refer [tr]]
            [weather.ui.view.view :as view]))

(defonce new-message (reagent/atom nil))

(defn send-message
  []
  (utils/http-post! "/api/messages"
                   @new-message
                   #(do
                      (reset! new-message nil)
                      (route/modify-location-hash! "#"))))

(defn atom-input
  [label-text atom key]
  [:div
   [:label label-text]
   [:input {:type "text"
            :value (key @atom)
            :on-change #(swap! atom assoc key (-> % .-target .-value))}]])

(defn new-message-form
  []
  [:div
   [atom-input (tr :sender) new-message :sender]
   [atom-input (tr :message) new-message :body]
   [:button {:type "button"
             :disabled (or
                         (empty? (:sender @new-message))
                         (empty? (:body @new-message)))
             :on-click send-message}
    (tr :submit)]])

(defmethod view/render-view "new-message" []
  [:div#content
   [:h2 "New message"]
   [new-message-form]])
