(ns police-rp.utils
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [cheshire.core :as cs]))


(defn string-keys-to-symbols [map] ; https://markhneedham.com/blog/2013/09/26/clojure-writing-json-to-a-filereading-json-from-a-file/
  (reduce #(assoc %1 (-> (key %2) keyword) (val %2)) {} map))

(defn read-config []
  (-> "../resources/config.json"
      io/resource
      slurp
      str/trim
      cs/parse-string
      string-keys-to-symbols))

(defn db-save [name data]
  ; https://dzone.com/articles/clojure-writing-json
  (spit (io/resource (str "../resources/" name ".json")) (cs/generate-string data {:pretty true})))

(defn db-get [name]
  (cs/parse-string (slurp (io/resource (str "../resources/" name ".json")))))