(ns weather.localization)

(def dictionary {:title "Weather with Clojure from Wunderground!"
                 :submit "Show me the weather!"})

(defn tr
  [key]
  (get dictionary key))
