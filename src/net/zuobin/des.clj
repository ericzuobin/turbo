(ns net.zuobin.des
  (:gen-class :main true)
  (:import (javax.crypto Cipher)
           (javax.crypto.spec SecretKeySpec))
  (:use [clojure.data.codec.base64]))


(defn- byte-method
  "获取key的byte数组。"
  [key]
  (.getBytes key))

(defn- orByte
  [c1 c2]
  (let [bit-value (bit-or (bit-shift-left (.indexOf "0123456789ABCDEF" (str c1)) 4) (.indexOf "0123456789ABCDEF" (str c2)))]
    (unchecked-byte bit-value)))

(defn- lecai-enc-method
  "lecai的加密方式"
  [key]
  (byte-array (map byte (let [length (quot (.length key) 2)]
      (loop [result [] x 0]
        (if (= x length)
          result
          (recur (conj result (orByte (nth key (* x 2)) (nth key (+ (* x 2) 1)))) (inc x))))))))



(defn base64ToString [b]
  "传入byte[] b,返回加密字符串"
  (String. (encode b) "UTF-8"))

(defn get-cipher
  "secret可为Des Aes等"
  [mode seed secret key-method]
  (let [key-spec (SecretKeySpec. (key-method seed) secret)
         cipher (Cipher/getInstance secret)]
     (.init cipher mode key-spec)
     cipher))

(defn- enc
  [text key secret key-method]
  (let [bytes (bytes text)
        cipher (get-cipher Cipher/ENCRYPT_MODE key secret key-method)]
    (base64ToString (.doFinal cipher bytes))))

(defn encrypt
  "通用的base64压缩加密算法.
  text:加密字符串,
  key:为八位密匙
  secret: DES,AES等
  "
  ([text key secret]
   (enc (byte-method text) key secret byte-method)))

(defn encrypt-lecai
  "公司的压缩加密算法.
  text:加密字符串,
  key:为十六位密匙
  secret: DES,AES等
  公司的密匙需经过特殊处理
  "
  ([text key secret]
   (enc (byte-method text) key secret lecai-enc-method)))

(defn -main
  [& args]
   (println (encrypt-lecai "dada" "0123456789ABCDEF" "DES"))
  )