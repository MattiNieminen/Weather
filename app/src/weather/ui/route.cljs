(ns weather.ui.route
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary :refer-macros [defroute]]))

;
; Current route
;
(defonce route (reagent/atom nil))

;
; Routes
;
(secretary/set-config! :prefix "#")

(secretary/defroute "/:city/:date" [city date]
  (reset! route {:city city
                 :date date}))

(secretary/defroute "*" []
  (reset! route nil))

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
