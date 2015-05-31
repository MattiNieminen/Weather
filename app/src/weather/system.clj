(ns weather.system
  (:require [weather.component.config :as config]
            [weather.component.mongodb :as mongodb]
            [weather.component.ring-context :as ring-context]
            [weather.component.httpkit :as httpkit]
            [com.stuartsierra.component :as component]))

(defn system
  [profile ring-handler]
  (component/system-map
    :config (config/new-config profile)
    :mongodb (component/using (mongodb/new-mongodb) [:config])
    :ring-context (component/using
                    (ring-context/new-ring-context ring-handler) [:mongodb])
    :httpkit (component/using (httpkit/new-httpkit) [:config :ring-context])))
