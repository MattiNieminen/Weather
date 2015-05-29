(defproject clj-web-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [com.stuartsierra/component "0.2.3"]
                 [metosin/potpuri "0.2.2"]
                 [http-kit "2.1.19"]]
  :main ^:skip-aot clj-web-example.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
