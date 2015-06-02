(ns weather.localization)

(def dictionary {:title "Weather with Clojure from Wunderground!"
                 :submit "Show me the weather!"
                 :data-arrived "Here is the weather data you wanted:"
                 :fog "Fog!"
                 :rain "Rain!"
                 :snow "Snow!"
                 :hail "Hail!"
                 :highest "Highest temp (celsius): "
                 :lowest "Lowest temp (celsius): "
                 :bad-request "Bad request! Maybe you are trying to look too
                               far in the future or in the past?"})

(defn tr
  [key]
  (get dictionary key))
