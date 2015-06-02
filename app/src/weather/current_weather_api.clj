(ns weather.current-weather-api
  (:require [schema.core :as s]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.string :as str]
            [ring.util.response :as response]))

(def apikey "ca497f499aa559d7")

(def CurrentWeather {:temp_c s/Num
                     :weather s/Str})

(defn wg-xml-has-data?
  [wg-data]
  (not (= :error (:tag (-> wg-data
                    zip/xml-zip
                    zip/down
                    zip/right
                    zip/right
                    zip/right)))))

(defn current-zipper
  [wg-data]
  (-> wg-data
    zip/xml-zip
    zip/down
    zip/right
    zip/right
    zip/right
    zip/children))

(defn filter-wg-data
  [wg-data]
  (for [xml-node (current-zipper wg-data)
        :let [tag (:tag xml-node)
              content (str/join (:content xml-node))]
        :when (some #{tag} [:temp_c :weather])]
    [tag (if
           (symbol? (read-string content))
           (str (read-string content))
           (read-string content))]))

(defn ->CurrentWeather
  [wg-data]
  (into {} (filter-wg-data wg-data)))

(defn get-weather-from-wg
  [apikey city]
  (xml/parse (str "http://api.wunderground.com/api/" apikey
                  "/conditions/q/" city ".xml")))

(defn get-weather
  [{params :params}]
  (let [city (:city params)
        wg-data (get-weather-from-wg apikey city)]
    (if (wg-xml-has-data? wg-data)
      (response/response (->CurrentWeather wg-data))
      (response/status (response/response {}) 400))))
