(ns weather.ui.utils
  (:require [cljs.core.async :refer [<! timeout]]
            [cljs-http.client :as http])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn http-get!
  [url f]
  (go
    (let [response (<! (http/get url))]
      (f response))))

(defn http-post!
  [url body f]
  (go
    (let [response (<! (http/post url {:edn-params body}))]
      (f response))))

(defn after-timeout
  [milliseconds f]
  (go
    (<! (timeout milliseconds))
    (f)))

;
; Application related
;

(defn ->celsius-str
  [s]
  (str s "°C"))
