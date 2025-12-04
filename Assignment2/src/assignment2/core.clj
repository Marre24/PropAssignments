(ns assignment2.core
  (:require [assignment2.functional :refer [my-max my-map my-checksum1]]))



(defn -main []
  (println (my-max '(1 2 3 55 4 5 6)))
  (println (my-max '(36)))
  (println (my-max '()))
  (println (my-map '(1 2 3 4 5 6) inc))
  (println (my-map '(-3 6 -9 12) pos?))
  (println (my-checksum1 '((9 1 3 9 5) (0) (6 8 5 4) (6 3 4 6 10) (7 7 7 7))))
  (println (my-checksum1 '((9 1 3 9 5) (0) (6 8 5 4) (1 3 4 6 10) (7 7 7 7)))))

