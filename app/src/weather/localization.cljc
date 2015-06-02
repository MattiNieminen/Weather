(ns weather.localization)

(def dictionary {:title "Weather with Clojure from Wunderground!"
                 :submit "Show me the weather!"
                 :current-weather "Here is the current weather:"
                 :data-arrived "Here is the weather data you wanted:"
                 :fog "There was some fog to be seen!"
                 :rain "It rained!"
                 :snow "It snowed!"
                 :hail "There was some hail!"
                 :highest "Highest temp (celsius): "
                 :lowest "Lowest temp (celsius): "
                 :bad-request "Bad request! Maybe you are trying to look too
                               far in the future or in the past?"})

(defn tr
  [key]
  (get dictionary key))
