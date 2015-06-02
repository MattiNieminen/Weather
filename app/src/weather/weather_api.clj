(ns weather.weather-api
  (:require [schema.core :as s]
            [weather.utils :as utils]
            [monger.collection :as mc]
            [ring.util.response :as response]
            [schema.coerce :as coerce]))

(def Weather {:maxtempm s/Int})

(defn get-weather
  [request]
  (response/response {:maxtempm 24}))
