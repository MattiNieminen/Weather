(ns weather.localization)

(def dictionary {:title "Weather with Clojure from Wunderground!"
                 :submit "Show me the weather!"
                 :data-arrived "Here is the weather data you wanted:"
                 :fog "There was some fog!"
                 :rain "It rained..."
                 :snow "It snowed!"
                 :hail "It hailed!"
                 :highest "Highest temp: "
                 :lowest "Lowest temp: "
                 :bad-request "Bad request! Maybe you are trying to look too
                               far in the future or in the past?"})

(defn tr
  [key]
  (get dictionary key))
