(ns weather.ui.view.controls
  (:require [reagent.core :as reagent]
            [cljs-pikaday.reagent :as pikaday]
            [weather.ui.state :as state]
            [weather.localization :refer [tr]]))

(defonce city (reagent/atom nil))
(defonce date (reagent/atom nil))

(defn add-zero
  [int]
  (if (< int 10) (str "0" int) int))

(defn format-date
  [date]
  (str (.getFullYear date) "-"
       (add-zero (+ 1 (.getMonth date))) "-"
       (add-zero (.getDate date))))

(defn current-date?
  [date]
  (let [current-date (js/Date.)]
    (if
      (and (= (.getFullYear date) (.getFullYear current-date))
           (= (.getMonth date) (.getMonth current-date))
           (= (.getDate date) (.getDate current-date)))
      true)))

;
; For modifying the location.hash
;
(defn route-from-atoms
  []
  (if
    (current-date? @date)
    (str "#/" @city)
    (str "#/" @city "/" (format-date @date))))

(defn modify-location-hash!
  [hash]
  (set! js/location.hash hash))

;
; Components
;
(defn city-select
  []
  [:select {:value @city
            :on-change #(reset! city (-> % .-target .-value))}
   [:option {:value "tampere_fi"} "Tampere"]
   [:option {:value "london_gb"} "London"]
   [:option {:value "durham_nc"} "Durham, North Carolina"]])

(defn controls
  [state]
  (reset! city (:city state))
  (reset! date (:date state))
  [:div#controls
   [city-select]
   [pikaday/date-selector {:date-atom date}]
   [:button {:type "button"
             :on-click #(modify-location-hash! (route-from-atoms))
             :id "submit-button"}
    (tr :submit)]])
