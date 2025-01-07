(defproject jepsen.hazelcast-server "0.1.0-SNAPSHOT"
  :description "A basic Hazelcast server"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src"]
  :java-source-paths ["java"]
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/tools.cli "1.1.230"]
                 [org.clojure/tools.logging "1.3.0"]
                 [spootnik/unilog "0.7.32"]]
  :profiles {:uberjar {:uberjar-name "hazelcast-server.jar"}}
  :main jepsen.hazelcast-server
  :aot [jepsen.hazelcast-server]
  :repositories {
    "hazelcast release" "https://repository.hazelcast.com/release/"
    "clojars" "https://repo.clojars.org/"
  }
  :plugins [[lein-git-deps "0.0.2"]]
  :git-dependencies [(str "https://" (System/getenv "GITHUB_TOKEN") "@github.com/ahmetmircik/hazelcast-mono.git"
                            "6.0/cp/chunked")]
  :checkout-deps-shares ^:replace [:source-paths :java-source-paths])

