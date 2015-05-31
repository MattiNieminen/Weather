(ns weather.routes
  (:require [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [weather.ui.index :as index]
            [compojure.route :as route]
            [weather.message-api :as message-api]))

(defapi app
  (swagger-ui "/swagger-ui")
  (swagger-docs {:info {:title "weather API"
                        :description "Example API for example application."}})
  
  (GET* "/" []
    :no-doc true
    (index/index-page))
  
  (route/resources "/static")
  
  (context* "/api" []
    :tags ["API"]
    
    (GET* "/messages" []
      :summary "Gets messages from MongoDB."
      :return  [message-api/SavableMessage]
      message-api/get-messages)
    
    (POST* "/messages" []
      :summary "Saves a message to MongoDB."
      :body [message message-api/Message]
      :return message-api/SavableMessage
      message-api/save-message)))
