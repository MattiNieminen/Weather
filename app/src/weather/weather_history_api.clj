(ns weather.weather-history-api
  (:require [schema.core :as s]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as str]
            [ring.util.response :as response]))

(def apikey "ca497f499aa559d7")

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

(defn summary-zipper
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

(defn filter-wg-data
  [wg-data]
  (for [xml-node (summary-zipper wg-data)
        :let [tag (:tag xml-node)
              content (str/join (:content xml-node))]
        :when (some #{tag} [:fog :rain :snow :hail :meantempm :maxtempm
                            :mintempm])]
    [tag (if
           (symbol? (read-string content))
           (str (read-string content))
           (read-string content))]))

(defn ->WeatherHistory
  [wg-data]
  (into {} (filter-wg-data wg-data)))

(defn get-weather-from-wg
  [apikey date city]
  (xml/parse (str "http://api.wunderground.com/api/" apikey "/history_"
                  (str/replace date #"-" "") "/q/" city ".xml")))

(defn get-weather
  [{params :params}]
  (let [date (:date params)
        city (:city params)
        wg-data (get-weather-from-wg apikey date city)]
    (if (wg-xml-has-data? wg-data)
      (response/response (->WeatherHistory wg-data))
      (response/status (response/response {}) 400))))
