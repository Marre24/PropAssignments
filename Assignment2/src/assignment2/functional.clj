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

(defn get-diff
  ([list]
   (if (empty? (rest list))
     0
     (get-diff (rest list) (first list) (first list))))
  ([list min max]
   (let [head (first list) tail (rest list)]
     (if (empty? list)
       (- max min)
       (if (< max head)
         (recur tail min head)
         (if (> min head)
           (recur tail head max)
           (recur tail min max)))))))

(defn my-checksum1
  ([list]
   (my-checksum1 list 0))
  ([list sum]
   (if (empty? list)
     sum
     (recur (rest list) (+ sum (get-diff (first list)))))))

(defn my-checksum2
  ([list check-func total-func]
   (my-checksum2 (rest list) check-func total-func (check-func (first list))))
  ([list check-func total-func sum]
   (if (empty? list)
     sum
     (recur (rest list) check-func total-func (total-func sum (check-func (first list)))))))