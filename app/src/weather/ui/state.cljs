(ns weather.ui.state
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :refer-macros [defroute]]
            [weather.ui.utils :as utils]))

;
; Current state
;
(defonce state (reagent/atom nil))

;
; Connecting to backend (history)
;
(defn history-api-route
  [city date]
  (str "/api/weather/" city "/" date))

(defn get-weather-history-from-backend
  [city date]
  (utils/http-get!
    (history-api-route city date)
    #(reset! state {:city city
                    :date (js/Date. date)
                    :weather-history-data %})))

;
; Connecting to backend (current)
;
(defn current-api-route
  [city]
  (str "/api/weather/" city))

(defn get-current-weather-from-backend
  [city]
  (utils/http-get!
    (current-api-route city)
    #(reset! state {:city city
                    :date (js/Date.)
                    :current-weather-data %})))

;
; Routes
;
(secretary/set-config! :prefix "#")

(secretary/defroute "/:city/:date" [city date]
  (get-weather-history-from-backend city date))

(secretary/defroute "/:city" [city]
  (get-current-weather-from-backend city))

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
