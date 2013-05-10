(defproject pinyin4clj "0.2.0-SNAPSHOT"
  :description "获取汉字拼音的Clojure库，对pinyin4j进行了简单封装。"
  :url "http://github.com/Ruiyun/pinyin4clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.belerweb/pinyin4j "2.5.0"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.5.1"]]}})
