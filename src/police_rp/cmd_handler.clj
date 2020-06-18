(ns police-rp.cmd_handler
  (:gen-class)
  (:require
   [discljord.messaging]
   [police-rp.commands.test :as test]
   [police-rp.commands.rp-init :as init]))

(declare commands)

(defn help-cmd [state & [options]]
  (doseq [c commands]
    (println (str c)))
  (discljord.messaging/create-message!
   (:messaging @state)
   (:channel-id options)
   :embed {:title "Page d'aide de Police-RP-Bot"
           :color 0x0000FF
           :fields [{:name "Commandes disponibles:"
                     :value "e"}]})) ; lister les commandes

(def commands
  {"test" #'test/execute
   "rp-init" #'init/execute
   "help" #'help-cmd})

(defn redirect [cmd state & [options]]
  (if-let [command (commands cmd)]
    (command state options)
    (discljord.messaging/create-message!
     (:messaging @state)
     (:channel-id options)
     :content (str " Commande `" cmd "` introuvable"))))