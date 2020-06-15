(ns police-rp.cmd_handler
  (:gen-class)
  (:require
   [discljord.messaging]
   [police-rp.commands.test]
   [police-rp.commands.rp_init]))

(def commands
  {"test" #'police-rp.commands.test/execute
   "rp-init" #'police-rp.commands.rp_init/execute})

(defn redirect [cmd state & [options]]
  (if (nil? (commands cmd))
    (discljord.messaging/create-message! 
     (:messaging @state) 
     (:channel-id options) 
     :content (str " Commande `" cmd "` introuvable"))
    ((commands cmd) state options)))

