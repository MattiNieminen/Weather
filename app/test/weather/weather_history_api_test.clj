(ns weather.weather-history-api-test
  (:require [clojure.test :refer :all]
            [weather.weather-history-api :refer :all]
            [clojure.java.io :as io]
            [clojure.xml :as xml]))

(def history-xml (xml/parse "resources/test/history.xml"))
(def empty-history-xml (xml/parse "resources/test/empty_history.xml"))

(deftest wg-xml-has-data?-test
  (testing "Xmls without data are identified without exceptions."
    (is (= true (wg-xml-has-data? history-xml)))
    (is (= false (wg-xml-has-data? empty-history-xml)))))

(deftest history-zipper-test
  (testing "Zipper finds the node that contains the weather data."
    (let [zipper-result (history-zipper history-xml)]
      (is (= (second zipper-result)
             {:tag :fog :attrs nil :content ["1"]}))
      (is (= (nth zipper-result 28)
             {:tag :maxtempm :attrs nil :content ["3"]})))))

(deftest ->WeatherHistory-test
  (testing "WeatherHistory is formed correctly."
   (is (= (->WeatherHistory history-xml)
          {:fog 1 :rain 1 :snow 1 :hail 0
           :meantempm 2 :maxtempm 3 :mintempm 1}))))
