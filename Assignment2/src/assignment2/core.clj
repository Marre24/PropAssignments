(ns assignment2.core
  (:require [assignment2.functional :refer [my-max my-map]]))



(defn -main []
  (println (my-max '(1 2 3 55 4 5 6)))
  (println (my-max '(36)))
  (println (my-max '()))
  (println (my-map '(1 2 3 4 5 6) inc))
  (println (my-map '(-3 6 -9 12) pos?)))

