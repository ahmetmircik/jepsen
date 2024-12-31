(defproject jepsen.hazelcast "0.1.0-SNAPSHOT"
  :description "Jepsen tests for Hazelcast IMDG"
  :url "http://jepsen.io/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [jepsen "0.3.4"]]
  :repositories {"hazelcast release" "https://repository.hazelcast.com/release/"}
  :aot [jepsen.hazelcast]
  :main jepsen.hazelcast
  :plugins [[lein-git-deps "0.0.1"]]
  :git-dependencies [["https://github.com/ahmetmircik/hazelcast-mono.git"
                      "6.0/cp/chunked"]]
  :checkout-deps-shares ^:replace [:source-paths :java-source-paths])
