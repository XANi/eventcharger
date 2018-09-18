(ns eventcharger.core
  (:use [clojure.pprint])
  (:require [yaml.core :as yaml]
            [clojurewerkz.machine-head.client :as mqtt]
            [cheshire.core :as json]
            )
  (:import (com.fasterxml.jackson.core JsonParseException)))



;(require '[yaml.core :as yaml])
(println "Starting eventcharger")
(def config (yaml/from-file "cfg/eventcharger.dev.yaml"))
(pprint config)

;(require '[clojurewerkz.machine-head.client :as mqtt])

;(defn event-handler
(defn event-handler [^String topic _ ^bytes payload]
  (print (str topic) ": ")
  (let [payload-str (apply str (map char payload))]
    (pprint (json/parse-string payload-str))

    )
  )
(defn handle-json-err [e json]
  (println "error parsing JSON string ["  (String. json  "UTF-8")"]): ")
  (println (.getMessage e))
  ;(println (type e))
  ;(pprint e)
  )
(defn handle-generic-err [e]
  (println (.getMessage e))
  (println (type e))
  ;(pprint e)
  )



(defn event-handler-ex [^String topic _ ^bytes payload]
  (print (str topic) ": ")
  (try
    (let [payload-str (String. payload "UTF-8")]
       (pprint (json/parse-string payload-str)))
    (catch JsonParseException e (handle-json-err e payload))
    (catch Throwable e  (handle-generic-err e))
    )
      ;(finally (println "bad json")) ;  this is ran regardless of try
  )




(def conn (mqtt/connect (:address (:default (:bus config)))
                        {:opts
                         {:username (:user (:default (:bus config)))
                          :password (:pass (:default (:bus config)))}}))


(mqtt/subscribe conn {"metrics/#" 0} event-handler-ex)
;(mqtt/subscribe conn {"events/#" 0 "metrics/#" 0} event-handler-ex)
                ;(fn [^String topic _ ^bytes payload]
                ;  (print (str topic) ": ")
                ;   (pprint (json/parse-string (apply str (map char payload))))
                ;    ))

;                  (print (str topic) ": ")
;                         (println (apply str (map char payload)))))
;(mqtt/publish conn "discovery" (str (ns-name *ns*)))