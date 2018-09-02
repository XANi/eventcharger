(ns eventcharger.core
  (:use [clojure.pprint])
  (:require [yaml.core :as yaml]
            [clojurewerkz.machine-head.client :as mqtt]))



;(require '[yaml.core :as yaml])
(println "Starting eventcharger")
(def config (yaml/from-file "cfg/eventcharger.dev.yaml"))
(pprint config)

;(require '[clojurewerkz.machine-head.client :as mqtt])



(def conn (mqtt/connect (:address (:default (:bus config)))
                        {:opts
                         {:username (:user (:default (:bus config)))
                          :password (:pass (:default (:bus config)))}}))


(mqtt/subscribe conn {"metrics/+" 0}
                (fn [^String topic _ ^bytes payload]
                  (print (str topic ": ")
                         (println (apply str ((map char payload)))))))

(mqtt/publish conn "discovery" (str (ns-name *ns*)))