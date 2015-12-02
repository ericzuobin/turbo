(ns net.zuobin.turbo)

;该项目是个人整理开发的常用方法的一个集合。
;让本项目给你个人的项目turbo加速一下吧。

(defn md5-str
  "返回参数字符串的md5值。"
  [exprs]
  (apply str
         (map (partial format "%02x")
              (.digest (doto (java.security.MessageDigest/getInstance "MD5")
                         .reset (.update (.getBytes exprs)))))))
(defn encrypt-lecai
  "公司用base64压缩加密算法,passType可为DES,AES等 ,
  密匙key为16位长度字符串。"
  [text key passType]
  (net.zuobin.des/encrypt-lecai text key passType))

(defn encrypt
  "通用的base64压缩加密算法，passType可为DES,AES等 ,
  密匙key为8位字符串"
  [text key passType]
  (net.zuobin.des/encrypt text key passType))

(defn base64-str
  "将所传参数base64压缩"
  [text]
  (net.zuobin.des/base64ToString text))





