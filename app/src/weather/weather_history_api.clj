(ns weather.weather-history-api
  (:require [schema.core :as s]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as str]
            [monger.collection :as mc]
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

(defn get-weather-from-database
  [database city date]
  (:wg-data (mc/find-one-as-map database :weather-history {:city city
                                                           :date date})))

(defn insert-weather-to-database
  [database city date wg-data]
  (mc/insert database :weather-history {:_id (utils/object-id)
                                        :city city
                                        :date date
                                        :wg-data wg-data}))

(defn get-weather
  [wg-apikey database city date]
  (if-let [wg-data (get-weather-from-database database city date)]
    (->WeatherHistory wg-data)
    (let [wg-data (get-weather-from-wg wg-apikey date city)]
      (if (wg-xml-has-data? wg-data)
        (do
          (insert-weather-to-database database city date wg-data)
          (->WeatherHistory wg-data))
        {}))))
