(ns assignment2.safe)

(defmacro safe
  ([expr] `(try ~expr (catch Exception e# e#)))
  ([binds expr]
   (let [resource (first binds) value (second binds)]
     `(let [~resource nil]
        (try
          (let [~resource ~value] ~expr)
          (catch Exception e# e#)
          (finally
            (if (and ~resource (instance? java.io.Closeable ~resource))
              (try (.close ~resource) (catch Exception _#)))))))))

;; En funktion i Clojure tar emot värden som redan har utvärderats eftersom utvärdering 
;; sker direkt "eager". Makron får istället tillgång till den oevaluerade koden vid 
;; expansionstid. Detta gör att macron används för att styra hur och när den koden ska 
;; utvärderas. En identisk implementation kan inte göras med enbart funktioner. Men en 
;; kompromiss kan ske om man wrappar koden man vill köra med ett #-tecken för att göra 
;; om koden till en anonym funktion. 

;; Makro:    (safe (/ 1 0))
;; Funktion: (safe #(/ 1 0))

;; Detta gör att koden inte evalueras innan denna används i funktionen. Men denna lösning 
;; kan inte läsa in och manipulera funktionens inre uttryck för att exempelvis stänga resurser.

;; Alltså kan inte en funktion användas med en identisk användning. Detta fungerar inte 
;; då reslutatet redan kommer ha utvärderats och då kommer redan ha kastat ett "exception". 
;; Dessutom kommer inte resurser kunna stängas då detta inte hanteras av själva funktionen 
;; som skickas in utan utav "safe". 

;; Dessutom så kommer bindningarna som skickas in inte kunna användas av den anonyma funktionen 
;; som skickas in. Anledningen är att den anynoyma funktionen redna är kunstuerad innan bindningarna genereras. 
