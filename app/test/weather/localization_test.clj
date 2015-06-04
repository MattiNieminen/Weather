(ns weather.localization-test
  (:require [clojure.test :refer :all]
            [weather.localization :refer :all]))

(deftest tr-test
  (testing "Translations are fetched correctly."
    (is (= (tr :title) "Weather with Clojure from Wunderground!"))))
