(defproject jepsen.hazelcast "0.1.0-SNAPSHOT"
  :description "Jepsen tests for Hazelcast IMDG"
  :url "http://jepsen.io/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [jepsen "0.3.4"]
                 [com.hazelcast/hazelcast-enterprise "6.0.0-SNAPSHOT"
                  :git/url "https://github.com/ahmetmircik/hazelcast-mono.git"
                  :git/sha "6.0/cp/chunked"]]
  :repositories {"hazelcast release" "https://repository.hazelcast.com/release/"}
  :aot [jepsen.hazelcast]
  :main jepsen.hazelcast)