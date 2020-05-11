(defproject dotacards "0.1.0-SNAPSHOT"
  :description "DOTA Party Game"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [garden "1.3.9"]
                 [quickcljs "0.0.1"]]

  :quickcljs-view dotacards.core/app-view)


