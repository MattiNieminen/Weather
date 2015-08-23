(ns weather.weather-history-api-test
  (:require [clojure.test :refer :all]
            [weather.weather-history-api :refer :all]
            [clojure.java.io :as io]
            [clojure.xml :as xml]
            [weather.test-utils :as test-utils]
            [clj-time.core :as t]
            [monger.collection :as mc]))

(use-fixtures :each test-utils/system-fixture)

(def history-xml (xml/parse "resources/test/history.xml"))
(def empty-history-xml (xml/parse "resources/test/empty_history.xml"))

(deftest wg-xml-has-data?-test
  (testing "Xmls without data are identified without exceptions."
    (is (= true (wg-xml-has-data? history-xml)))
    (is (= false (wg-xml-has-data? empty-history-xml)))))

(deftest history-zipper-test
  (testing "Zipper finds the node that contains the weather data."
    (let [zipper-result (history-zipper history-xml)]
      (is (= {:tag :fog :attrs nil :content ["1"]}
             (second zipper-result)))
      (is (= {:tag :maxtempm :attrs nil :content ["3"]}
             (nth zipper-result 28))))))

(deftest ->WeatherHistory-test
  (testing "WeatherHistory is formed correctly."
   (is (= {:fog 1 :rain 1 :snow 1 :hail 0
           :meantempm 2 :maxtempm 3 :mintempm 1}
          (->WeatherHistory history-xml)))))

; APIKEY must be correct for this test to pass.
(deftest get-weather-test
  (testing "Weather data is fetched from database if it exists."
   (is (mc/empty? (test-utils/get-database) :weather-history))
   (get-weather (test-utils/get-wg-apikey)
                (test-utils/get-database)
                "Tampere"
                (t/local-date 2014 1 1))
   (is (= 1 (mc/count (test-utils/get-database) :weather-history)))))
