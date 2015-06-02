(ns weather.test-utils
  (:require [com.stuartsierra.component :as component]
            [weather.system :as system]
            [weather.routes :as routes]
            [monger.db :as db]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(defonce test-system (component/start (system/system "test" routes/app)))

(defn database-fixture
  [f]
  (db/drop-db (get-in test-system [:mongodb :database]))
  (f))

(defn http-get
  [url]
  (let [response @(http/get url)
        body (json/read-json (:body response))]
    (assoc response :body body)))
