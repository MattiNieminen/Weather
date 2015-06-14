(ns weather.routes
  (:require [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [weather.ui.index :as index]
            [compojure.route :as route]
            [weather.current-weather-api :as current-api]
            [weather.weather-history-api :as history-api]
            [ring.util.response :as response])
  (:import [org.joda.time LocalDate]))

(defapi app
  (swagger-ui "/swagger-ui")
  (swagger-docs {:info {:title "weather API"
                        :description "API for getting and caching weather from
                                      Wunderground"}})
  
  (GET* "/" []
    :no-doc true
    (index/index-page))
  
  (route/resources "/static")
  
  (context* "/api" []
    :tags ["API"]
    
    (GET* "/weather/:city" request
      :summary "Gets current weather either from database (if exists) or
                Wunderground."
      :path-params [city :- s/Str]
      :return current-api/CurrentWeather
     (response/response (current-api/get-weather
                          (:wg-apikey request)
                          (:database request)
                          city)))
    
    (GET* "/weather/:city/:date" request
      :summary "Gets weather history either from database (if exists) or
                Wunderground."
      :path-params [city :- s/Str, date :- LocalDate]
      :return history-api/WeatherHistory
      (response/response (history-api/get-weather
                           (:wg-apikey request)
                           (:database request)
                           city
                           date)))))
