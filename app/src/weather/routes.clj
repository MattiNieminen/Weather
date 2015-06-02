(ns weather.routes
  (:require [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [weather.ui.index :as index]
            [compojure.route :as route]
            [weather.weather-api :as weather-api])
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
    
    (GET* "/weather/:city/:date" []
      :summary "Gets weather either from database (if exists) or Wunderground."
      :path-params [city :- s/Str, date :- LocalDate]
      :return weather-api/Weather
      weather-api/get-weather)))
