# pinyin4clj

pinyin4clj是一个用于获取汉字拼音的Clojure库，其内部对pinyin4j进行了简单封装。

## 安装
在你`project.clj`文件的依赖项中添加：

    [pinyin4clj "0.1.0"]

## 使用方法
```clojure
(use 'pinyin4clj.core)
(pinyin "中文")
-> "zhōngwén"
```

### opts设置项说明

#### :case 输出大小写设置
>
> * :lower-case        大写输出
> * :upper-case        小写输出

#### :tone 音调标记设置
>
> * :without-tone      不标记音调
> * :with-tone-mark    直接在输出拼音上标记音调
> * :with-tone-number  使用数字1 2 3 4来表示音调

#### :v-char 拼音`ü`的显示设置
>
> * :with-u-and-colon  使用`u:`来表示
> * :with-u-unicode    直接表示为`ü`
> * :with-v            使用字母`v`来表示

#### :separator 每个汉字之间的分隔符
> 若不设置，表示汉字之间的拼音没有间隔

## 更多示例
```clojure
(pinyin "女子")
-> "nǚzi"

(pinyin "女子" :separator \space)
-> "nǚ zi"

(pinyin "女子" :tone :without-tone :v-char :with-v)
-> "nvzi"

(pinyin "女子的英文是lady")
-> "nǚzideyīngwénshìlady"

(pinyin "女子的英文是lady" :separator " ")
-> "nǚ zi de yīng wén shì lady"
```

## License

Copyright © 2013 ruiyun.wen@gmail.com

Distributed under the Eclipse Public License, the same as Clojure.
