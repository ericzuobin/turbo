(ns net.zuobin.des
  (:gen-class :main true)
  (:import (javax.crypto Cipher)
           (javax.crypto.spec SecretKeySpec))
  (:use [clojure.data.codec.base64]))


(defn- byte-method
  "获取key的byte数组。"
  [key]
  (.getBytes key))

(defn lecai-enc-method
  "docstring"
  [key]

  )

(defn base64ToString [b]
  "传入byte[] b,返回数组"
  (String. (encode b) "UTF-8"))

(defn get-cipher
  "secret可为Des Aes等"
  [mode seed secret key-method]
  (let [key-spec (SecretKeySpec. (key-method seed) secret)
         cipher (Cipher/getInstance secret)]
     (.init cipher mode key-spec)
     cipher))

(defn enc
  [text key secret key-method]
  (let [bytes (bytes text)
        cipher (get-cipher Cipher/ENCRYPT_MODE key secret key-method)]
    (base64ToString (.doFinal cipher bytes))))

(defn encrypt
  "公司的压缩加密算法.
  text:加密字符串,
  key:为十六位密匙
  secret: DES,AES等
  enc-byte-methode:定制key方法,默认就是getbytes[]
  "
  ([text key secret]
   (enc (byte-method text) key secret byte-method))
  ([text key secret enc-byte-methode]
   (enc (byte-method text) key secret enc-byte-methode)))

(defn -main
  [& args]
  (println (encrypt "dada" "12345678" "DES"))
  )