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

(defn my-map
  ([list func]
   (my-map list func '()))
  ([list func result]
   (if (empty? list)
     (reverse result)
     (recur (rest list) func (conj result (func (first list)))))))