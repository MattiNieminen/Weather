(ns weather.weather-history-api
  (:require [schema.core :as s]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as str]
            [weather.utils :as utils]))

(def WeatherHistory {:fog s/Num
                     :rain s/Num
                     :snow s/Num
                     :hail s/Num
                     :meantempm s/Num
                     :maxtempm s/Num
                     :mintempm s/Num})

(defn wg-xml-has-data?
  [wg-data]
  (not (empty? (-> wg-data
                 zip/xml-zip
                 zip/down
                 zip/right
                 zip/right
                 zip/right
                 zip/down
                 zip/right
                 zip/right
                 zip/right
                 zip/children))))

(defn history-zipper
  [wg-data]
  (-> wg-data
    zip/xml-zip
    zip/down
    zip/right
    zip/right
    zip/right
    zip/down
    zip/right
    zip/right
    zip/right
    zip/down
    zip/children))

(defn ->WeatherHistory
  [wg-data]
  (into {} (utils/filter-wg-data
             wg-data
             history-zipper
             [:fog :rain :snow :hail :meantempm :maxtempm :mintempm])))

(defn get-weather-from-wg
  [apikey date city]
  (xml/parse (str "http://api.wunderground.com/api/" apikey "/history_"
                  (str/replace date #"-" "") "/q/" city ".xml")))

(defn get-weather
  [wg-apikey database city date]
  (let [wg-data (get-weather-from-wg wg-apikey date city)]
    (if (wg-xml-has-data? wg-data)
      (->WeatherHistory wg-data)
      {})))
