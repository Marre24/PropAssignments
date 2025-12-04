(ns assignment2.functional)

(defn my-max
  ([l]
   (if (empty? l)
     nil
     (my-max (first l) (rest l))))
  ([current-max l]
   (if (empty? l)
     current-max
     (if (< current-max (first l))
       (recur (first l) (rest l))
       (recur current-max (rest l))))))