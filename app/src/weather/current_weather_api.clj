(ns weather.current-weather-api
  (:require [schema.core :as s]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [weather.utils :as utils]
            [ring.util.response :as response]))

(def CurrentWeather {:temp_c s/Num
                     :weather s/Str})

(defn wg-xml-has-data?
  [wg-data]
  (not (= :error (:tag (-> wg-data
                         zip/xml-zip
                         zip/down
                         zip/right
                         zip/right
                         zip/right
                         zip/node)))))

(defn current-zipper
  [wg-data]
  (-> wg-data
    zip/xml-zip
    zip/down
    zip/right
    zip/right
    zip/right
    zip/children))

(defn ->CurrentWeather
  [wg-data]
  (into {} (utils/filter-wg-data wg-data current-zipper [:temp_c :weather])))

(defn get-weather-from-wg
  [apikey city]
  (xml/parse (str "http://api.wunderground.com/api/" apikey
                  "/conditions/q/" city ".xml")))

(defn get-weather
  [{params :params wg-apikey :wg-apikey}]
  (let [city (:city params)
        wg-data (get-weather-from-wg wg-apikey city)]
    (if (wg-xml-has-data? wg-data)
      (response/response (->CurrentWeather wg-data))
      (response/status (response/response {}) 400))))
