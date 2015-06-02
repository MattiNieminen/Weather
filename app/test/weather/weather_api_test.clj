(ns weather.weather-api-test
  (:require [clojure.test :refer :all]
            [weather.test-utils :as test-utils]
            [monger.collection :as mc]
            [weather.weather-api :refer :all]))

(use-fixtures :each test-utils/database-fixture)

(def url "http://localhost:8181/api/weather")

(def database (get-in test-utils/test-system [:mongodb :database]))

(deftest weather-api-test
  (testing "TODO Write tests!"
   (is (= 1 1))))
