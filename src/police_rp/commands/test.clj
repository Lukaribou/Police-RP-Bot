(ns police-rp.commands.test
  (:gen-class)
  (:require
   [discljord.messaging]))

(defn execute [state & [options]]
  (println "coucou")
  (discljord.messaging/create-message!
   (:messaging @state)
   (:channel-id (:channel-id options))
   :content (str "```Commande test\nArguments:" (:args options) "```")))