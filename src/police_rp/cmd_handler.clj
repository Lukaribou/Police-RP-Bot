(ns police-rp.cmd_handler
  (:gen-class)
  (:require
   [discljord.messaging]))

(defn redirect [cmd state & [options]]
  (try
    (require (symbol (str "police-rp.commands." cmd)))
    (eval (str "(police-rp.commands." cmd "/execute " state " " options ")"))
    (catch java.io.FileNotFoundException _
      (discljord.messaging/send-message!
       (:messaging @state)
       (:channel-id options)
       :content (str "Commande`" cmd "`non trouvée")))
    (catch Exception e
      (println (.getMessage e)))))
      ;(discljord.messaging/send-message!
       ;(:messaging @state)
       ;(:channel-id options)
       ;:content (str "Une erreur est survenue: " (.getMessage e))))))