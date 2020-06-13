(ns police-rp.core
  (:gen-class)
  (:require
   [discljord.connections :as c]
   [discljord.messaging :as m]
   [discljord.events :as e]
   [clojure.core.async :as a]
   [clojure.string]
   [police-rp.utils :as utils]))

(def config (utils/read-config))
(def state (atom nil))

(defmulti handle-event
  (fn [event-type _]
    event-type))

(defmethod handle-event :default
  [_ _])

(defmethod handle-event :ready
  [_ _]
  (c/status-update! (:connection @state) :activity (c/create-activity :name "the 911" :type :watch))) ;; Change l'activité du "Joue à"

(defmethod handle-event :message-create
  [_ {{bot :bot} :author :keys [channel-id content]}]
  (when-not bot
    (let [args (clojure.string/split content #" ")]
      (m/create-message! (:messaging @state) channel-id :content (str "```Commande test\nArguments:```" args)))))

(defn -main
  [& _]
  (let [event-ch (a/chan 100)
        connection-ch (c/connect-bot! (:token config) event-ch)
        messaging-ch (m/start-connection! (:token config))
        init-state {:connection connection-ch
                    :event event-ch
                    :messaging messaging-ch}]
    (reset! state init-state)
    (e/message-pump! event-ch handle-event)
    (m/stop-connection! messaging-ch)
    (c/disconnect-bot! connection-ch)))
