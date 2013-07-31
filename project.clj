(defproject pinyin4clj "0.3.0"
  :description "获取汉字拼音的Clojure库，对pinyin4j进行了简单封装。"
  :url "http://github.com/Ruiyun/pinyin4clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[ruiyun/pinyin4j "2.5.0"]]
  :plugins [[codox "0.6.4"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.5.1"]]}})
