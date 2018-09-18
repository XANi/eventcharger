(defproject eventcharger "0.1.0-SNAPSHOT"
  :description "Event management engine"
  :url "https://github.com/XANi/eventcharger"
  :license {:name "GPLv3"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [clj-http "2.0.0"]
                 [io.forward/yaml "1.0.9"]
                 [clojurewerkz/machine_head "1.0.0"]
                 [cheshire "5.8.0"]
                 ]
  :main ^:skip-aot eventcharger.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
