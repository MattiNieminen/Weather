(ns weather.location
  (:require [geo.core :as geo]))

(def cities {[51.500152 -0.126236] {:id "london_gb"
                                    :name "London"}
             [61.497978 23.764931] {:id "tampere_fi"
                                    :name "Tampere"}
             [36.026302 -79.10969] {:id "durham_nc"
                                    :name "Durham"}})

(defn point
  [tuple]
  (geo/point 4326 (first tuple) (second tuple)))

(defn haversine
  [origin destination]
  (geo/distance-to (point origin) (point destination)))

(defn closest-city
  [lat long]
  (cities
    (apply min-key (partial haversine [lat long]) (keys cities))))
