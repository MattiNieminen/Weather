(ns weather.utils
  (:require [schema.core :as s]
            [clojure.string :as str])
  (:import [org.joda.time DateTime]
           [org.joda.time DateTimeZone]))

;
; Monger (from Monger guide).
;
(DateTimeZone/setDefault DateTimeZone/UTC)

(defn object-id
  []
  (str (org.bson.types.ObjectId.)))

;
; Coercion helpers before saving documents to MongoDB.
;
(def Savable {:_id s/Str
              :timestamp DateTime})

(defn ->savable
  [coll]
  (assoc coll
         :_id (object-id)
         :timestamp (DateTime/now)))

;
; Weather related
;

; Complex, requires documentation and refactoring
(defn filter-wg-data
  [wg-data zipper tags]
  (for [xml-node (zipper wg-data)
        :let [tag (:tag xml-node)
              content (str/join (:content xml-node))]
        :when (some #{tag} tags)]
    [tag (if
           (symbol? (read-string content))
           (str content)
           (read-string content))]))