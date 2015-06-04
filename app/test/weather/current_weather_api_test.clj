(ns weather.current-weather-api-test
  (:require [clojure.test :refer :all]
            [weather.current-weather-api :refer :all]
            [clojure.java.io :as io]
            [clojure.xml :as xml]))

(def current-xml (xml/parse "resources/test/current.xml"))
(def empty-current-xml (xml/parse "resources/test/empty_current.xml"))

(deftest wg-xml-has-data?-test
  (testing "Xmls without data are identified without exceptions."
    (is (= true (wg-xml-has-data? current-xml)))
    (is (= false (wg-xml-has-data? empty-current-xml)))))

(deftest current-zipper-test
  (testing "Zipper finds the node that contains the weather data."
    (let [zipper-result (current-zipper current-xml)]
      (is (= (nth zipper-result 13)
             {:tag :weather, :attrs nil, :content ["Clear"]}))
      (is (= (nth zipper-result 16)
             {:tag :temp_c, :attrs nil, :content ["14.1"]})))))

(deftest ->CurrentWeather-test
  (testing "WeatherHistory is formed correctly."
   (is (= (->CurrentWeather current-xml) {:weather "Clear", :temp_c 14.1}))
   (is (= (->CurrentWeather empty-current-xml) {}))))
