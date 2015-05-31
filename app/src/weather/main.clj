(ns weather.main
  (:gen-class)
  (:require [weather.system :as system]
            [com.stuartsierra.component :as component]
            [weather.routes :as routes]))

(defn -main
  [& args]
  (component/start (system/system (first args) routes/app)))
