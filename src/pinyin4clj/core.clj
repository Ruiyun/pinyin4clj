(ns pinyin4clj.core
  "pinyin4clj对pinyin4j进行了简单的封装，提供更符合clojure惯例的调用方式。

   函数'pinyin'可以通过传入选项来控制输出的拼音格式。
   而函数'ascii-pinyin'，则可以生成便于与键盘输入进行比较的格式，对于自动完成等功能而言，非常方便。"
  (:refer-clojure :exclude [replace])
  (:require [clojure.string :refer [replace upper-case]])
  (:import [net.sourceforge.pinyin4j PinyinHelper]
           [net.sourceforge.pinyin4j.format HanyuPinyinOutputFormat HanyuPinyinCaseType HanyuPinyinToneType HanyuPinyinVCharType]))

(defn- kw->fmt-type [kw]
  (case kw
    :lower-case       HanyuPinyinCaseType/LOWERCASE
    :upper-case       HanyuPinyinCaseType/UPPERCASE

    :with-tone-mark   HanyuPinyinToneType/WITH_TONE_MARK
    :with-tone-number HanyuPinyinToneType/WITH_TONE_NUMBER
    :without-tone     HanyuPinyinToneType/WITHOUT_TONE

    :with-u-unicode   HanyuPinyinVCharType/WITH_U_UNICODE
    :with-u-and-colon HanyuPinyinVCharType/WITH_U_AND_COLON
    :with-v           HanyuPinyinVCharType/WITH_V))

(defn- output-fmt [{:keys [case tone v-char] :or {case :lower-case, tone :with-tone-mark, v-char :with-u-unicode}}]
  (doto (HanyuPinyinOutputFormat.)
    (.setCaseType (kw->fmt-type case))
    (.setToneType (kw->fmt-type tone))
    (.setVCharType (kw->fmt-type v-char))))

(def ^:private default-py-fmt (output-fmt nil))

(defn- py-char [c fmt]
  (if-let [[py] (PinyinHelper/toHanyuPinyinStringArray c fmt)]
    [:py py]
    [:char c]))

(defn- recomb [coll]
  (reduce (fn [result [curr-type curr-val :as curr]]
            (let [[prev-type prev-val] (last result)]
              (if (= curr-type :char)
                (if (= prev-type :char-seq)
                  (-> (pop result)
                      (conj [:char-seq (conj prev-val curr-val)]))
                  (conj result [:char-seq [curr-val]]))
                (conj result curr))))
          [] coll))

(defn- remove-type [coll]
  (mapv (fn [[type val]]
          (if (= type :char-seq)
            (apply str val)
            val))
        coll))

(defn pinyin
  "opts设置项说明
     :case 输出大小写设置
       :lower-case        大写输出
       :upper-case        小写输出

     :tone 音调标记设置
       :without-tone      不标记音调
       :with-tone-mark    直接在输出拼音上标记音调
       :with-tone-number  使用数字1 2 3 4来表示音调

     :v-char 韵母'ü'的显示设置
       :with-u-and-colon  使用'u:'来表示
       :with-u-unicode    直接表示为ü
       :with-v            使用字母'v'来表示

     :separator 每个汉字之间的分隔符，若不设置，表示汉字之间的拼音没有间隔

   调用示例：
      (pinyin \"女子\")
      -> \"nǚzi\"

      (pinyin \"女子\" {:separator \\space})
      -> \"nǚ zi\"

      (pinyin \"女子\" {:tone :without-tone, :v-char :with-v})
      -> \"nvzi\""
  ([s] (pinyin s nil))
  ([s {separator :separator :as opts}]
     (let [fmt (if opts
                 (output-fmt opts)
                 default-py-fmt)]
       (->> (mapv #(py-char % fmt) s)
            (recomb)
            (remove-type)
            (interpose separator)
            (apply str)))))

(defn ascii-pinyin
  "仅用便于键盘录入的ascii字符来表示汉字拼音
   等同于调用pinyin函数时，设置了{:tone :without-tone, :v-char :with-v}

   (ascii-pinyin \"女子\")
   -> \"nvzi\""
  [s]
  (pinyin s {:tone :without-tone, :v-char :with-v}))
