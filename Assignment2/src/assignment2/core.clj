(ns assignment2.core
  (:require [assignment2.functional :refer [my-max]]))



(defn -main []
  (println (my-max '(1 2 3 55 4 5 6)))
  (println (my-max '(36)))
  (println (my-max '())))