(ns weather.component.ring-context
  (:require [com.stuartsierra.component :as component]))

(defn wrap-context
  [handler wg-apikey database]
  (fn [request]
    (handler (assoc request
                    :wg-apikey wg-apikey
                    :database database))))

(defrecord Ring-Context [config mongodb initial-handler]
  component/Lifecycle
  
  (start [this]
    (assoc this :handler (wrap-context initial-handler
                                       (get-in config [:loaded :wg-apikey])
                                       (:database mongodb))))
  
  (stop [this]
    (dissoc this :handler)))

(defn new-ring-context
  [handler]
  (map->Ring-Context {:initial-handler handler}))
