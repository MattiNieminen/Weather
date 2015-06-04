(ns weather.location-test
  (:require [clojure.test :refer :all]
            [weather.location :refer :all]))

(deftest closest-city-test
  (testing "Closest city is found correctly."
    (is (= {:id "tampere_fi", :name "Tampere"}
           (closest-city 60.75916 23.554688)))
    (is (= {:id "london_gb", :name "London"}
           (closest-city 48.458352 11.953125)))    
    (is (= {:id "durham_nc", :name "Durham"}
           (closest-city 25.799891 -65.566406)))))
