(ns adventofcode_2019.day1.day_1)

(def classpath
  (let [namespace-name (name (ns-name *ns*))
        package (clojure.string/split namespace-name #"\.")]
    (->>
      package
      (drop-last)
      (interpose "/")
      (clojure.string/join)
      )))

(defn classpath-file [file]
  (str classpath "/" file))


(defn input [x]
  (->
    x
    classpath-file
    clojure.java.io/resource
    clojure.java.io/reader
    line-seq
    ))

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
     

