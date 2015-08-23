(ns weather.test-utils
  (:require [com.stuartsierra.component :as component]
            [weather.system :as system]
            [weather.routes :as routes]
            [monger.db :as db]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(defonce test-system nil)

(defn system-fixture
  [f]
  (alter-var-root #'test-system (constantly (system/system "test" routes/app)))
  (alter-var-root #'test-system component/start)
  (db/drop-db (get-in test-system [:mongodb :database]))
  (f)
  (alter-var-root #'test-system (fn [s] (when s (component/stop s)))))

(defn get-wg-apikey
  []
  (get-in test-system [:config :loaded :wg-apikey]))

(defn get-database
  []
  (get-in test-system [:mongodb :database]))

(defn http-get
  [url]
  (let [response @(http/get url)
        body (json/read-json (:body response))]
    (assoc response :body body)))
