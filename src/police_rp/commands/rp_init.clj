(ns police-rp.commands.rp-init
  (:gen-class)
  (:require
   [discljord.messaging :as msg]
   [police-rp.utils :as utils]))

(defn execute [state & [options]]
  (if-let [user (get (utils/db-get "accounts") (:id (:author options)))]
    (msg/create-message! (:messaging @state) (:channel-id options) :content "Vous possédez déjà un compte.")
    (msg/create-message! (:messaging @state) (:channel-id options) :content "coucou")))