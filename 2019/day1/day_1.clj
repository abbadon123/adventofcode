(defn input [x] (line-seq (clojure.java.io/reader x)))
(defn fuel [x] (max 0 (.longValue (- (/ (Long. x) 3) 2))))

(defn fuelOfFuel [x]
  (if (<= (fuel x) 0)
    0

    (do (println (fuel x)) (+ (fuel x) (fuel (fuel x))))    
  )
)

(fuel "5")
(fuelOfFuel "1969")
(println (reduce + (map fuel (input "1_input"))))
(println (reduce + (map fuelOfFuel (input "2_input"))))
     


(println (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))
