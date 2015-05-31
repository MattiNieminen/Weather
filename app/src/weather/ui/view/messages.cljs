(ns weather.ui.view.messages
  (:require [reagent.core :as reagent]
            [weather.ui.utils :as utils]
            [weather.ui.route :as route]
            [weather.ui.view.view :as view]))

(defonce messages-state (reagent/atom nil))

(defn get-messages
  []
  (utils/http-get! "/api/messages" #(reset! messages-state (:body %))))

(defn message
  [msg]
  [:div
   [:h3 (:sender msg)]
   [:p (:body msg)]])

(defn messages
  []
  [:div#content
   [:h2 "Messages"]
   [:div
    (for [msg @messages-state]
      ^{:key (:_id msg)} [message msg])]])

(def messages-with-callback
  (with-meta messages {:component-did-mount get-messages}))

(defmethod view/render-view "messages" []
  [messages-with-callback])
