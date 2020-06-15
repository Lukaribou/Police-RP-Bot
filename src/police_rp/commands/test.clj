(ns police-rp.commands.test
  (:gen-class)
  (:require
   [discljord.messaging]))

(defn execute [state & [options]]
  (discljord.messaging/create-message! (:messaging @state) (:channel-id options) :content (str "```Commande test\nArguments:" (:args options) "```")))