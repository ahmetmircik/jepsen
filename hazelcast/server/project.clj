(defproject jepsen.hazelcast-server "0.1.0-SNAPSHOT"
  :description "A basic Hazelcast server"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src"]
  :java-source-paths ["java"]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.cli "1.0.219"]
                 [org.clojure/tools.logging "1.2.4"]
                 [spootnik/unilog "0.7.31"]]
  :profiles {:uberjar {:uberjar-name "hazelcast-server.jar"}}
  :main jepsen.hazelcast-server
  :aot [jepsen.hazelcast-server]
  :repositories {
    "hazelcast release" "https://repository.hazelcast.com/release/"
    "clojars" "https://repo.clojars.org/"
  }
  :plugins [[lein-git-deps "0.0.2"]]
  :git-dependencies [["https://github.com/ahmetmircik/hazelcast-mono.git"
                      "6.0/cp/chunked"]]
  :checkout-deps-shares ^:replace [:source-paths :java-source-paths])

