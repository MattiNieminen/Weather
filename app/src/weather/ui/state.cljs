(ns weather.ui.state
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :refer-macros [defroute]]
            [weather.ui.utils :as utils]))

;
; Current state
;
(defonce state (reagent/atom nil))

;
; Connecting to backend
;
(defn api-route
  [city date]
  (str "/api/weather/" city "/" date))

(defn get-weather-from-backend
  [city date]
  (utils/http-get! (api-route city date) #(reset! state {:city city
                                                         :date date
                                                         :weather-data %})))

;
; Routes
;
(secretary/set-config! :prefix "#")

(secretary/defroute "/:city/:date" [city date]
  (reset! state (get-weather-from-backend city date)))

(secretary/defroute "*" []
  (reset! state nil))

;
; Listener
;
(defn update-route!
  [_]
  (secretary/dispatch! js/location.hash))

(defn init-hook!
  []
  (set! js/window.onhashchange update-route!)
  (update-route! nil))

;
; For modifying the location.hash
;
(defn modify-location-hash!
  [hash]
  (set! js/location.hash hash))
