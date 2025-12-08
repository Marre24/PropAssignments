(ns assignment2.core
  (:require [assignment2.safe :refer [safe]])
  (:require [assignment2.functional :refer [my-max my-map my-checksum1 my-checksum2 my-reverse]])
  (:import java.io.FileReader java.io.File))



(defn -main []
  (println (my-max '(1 2 3 55 4 5 6)))
  (println (my-max '(36)))
  (println (my-max '()))
  (println (my-map '(1 2 3 4 5 6) inc))
  (println (my-map '(-3 6 -9 12) pos?))
  (println (my-checksum1 '((9 1 3 9 5) (0) (6 8 5 4) (6 3 4 6 10) (7 7 7 7))))
  (println (my-checksum1 '((9 1 3 9 5) (0) (6 8 5 4) (1 3 4 6 10) (7 7 7 7))))
  (println (my-checksum2 '((9 1 3 9 5) (6 8 5 4) (7 7 7 7)) my-max -))
  (println (my-checksum2 '((1 3 9 5) (6 5 4 3) (3 5 1)) my-max -))
  (println (my-reverse '(1 2 3 4 5 6)))
  (println (my-reverse '(36)))
  (println (my-reverse '()))
  (def v (safe (/ 1 0)))
  (println (str "should cast exception: " v))
  (def v (safe (/ 10 2)))
  (println (str "should be 5 and was: " v))
  (def v (safe [s (FileReader. (File. "file.txt"))] (.read s)))
  (println (str "should be 49 and was: " v))
  (def v (safe [s (FileReader. (File. "missing-file"))] (.read s)))
  (println (str "should cast exception: " v)))