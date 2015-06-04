(ns weather.ui.state
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :refer-macros [defroute]]
            [weather.ui.utils :as utils]
            [weather.location :as location]))

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
; Current weather with geolocation
;
(defn handle-geolocation-success
  [pos]
  (get-current-weather-from-backend
    (:id (location/closest-city (.-latitude js/pos.coords)
                                (.-latitude js/pos.coords)))))

(defn handle-geolocation-failure
  []
  (get-current-weather-from-backend (:id (val (first location/cities)))))

; So dirty, much bubblegum (https://bugzilla.mozilla.org/show_bug.cgi?id=675533)
(defn firefox-geolocation-fix
  []
  (utils/after-timeout 3000 #(if(nil? @state) (handle-geolocation-failure))))

(defn get-current-weather-from-backend-by-location
  []
  (let [geolocation (.-geolocation js/navigator)]
    (firefox-geolocation-fix)
    (if geolocation
      (.getCurrentPosition
        geolocation handle-geolocation-success handle-geolocation-failure)
      (handle-geolocation-failure))))

;
; Routes
;
(secretary/set-config! :prefix "#")

(secretary/defroute "/:city/:date" [city date]
  (get-weather-history-from-backend city date))

(secretary/defroute "/:city" [city]
  (get-current-weather-from-backend city))

(secretary/defroute "/" []
  (get-current-weather-from-backend-by-location))

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
