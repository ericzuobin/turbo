(ns net.zuobin.turbo)

;该项目是个人整理开发的常用方法的一个集合。
;让本项目给你个人的项目turbo加速一下吧。

(defn getMD5
  "Return the exprs's MD5."
  [exprs]
  (apply str
         (map (partial format "%02x")
              (.digest (doto (java.security.MessageDigest/getInstance "MD5")
                         .reset (.update (.getBytes exprs)))))))



