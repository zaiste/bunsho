(defproject bunsho "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [enlive "1.1.4"]
                 [ring/ring-json "0.2.0"]
                 [cheshire "5.2.0"]
                 [ring-middleware-format "0.3.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.clojure/data.json "0.2.3"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler bunsho.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
