(ns adventofcode-2019.day12.day-12
  (:require [clojure.test :refer :all]))

(defn moon
  ([position velocity] {
                        :position position
                        :velocity velocity
                        })
  ([position] (moon position (take (count position) (repeat 0))))
  )

(defn parse-input [input]
  (let [lines (clojure.string/split-lines input)
        line-to-numbers (partial re-seq #"-?\d+")
        numbers-to-ints (fn [numbers] (map #(Integer/parseInt %) numbers))]
    (map (comp moon numbers-to-ints line-to-numbers) lines)))

(defn apply-gravity [moon other]
  (let [one-dimension-gravity-change (fn [x1 x2] (cond
                                                   (< x1 x2) 1
                                                   (> x1 x2) -1
                                                   :default 0))]
    (assoc moon
      :velocity
      (map +
           (:velocity moon)
           (map one-dimension-gravity-change (:position moon) (:position other))))))

(defn apply-velocity [moon]
  (assoc moon :position (map + (:position moon) (:velocity moon))))

(defn steps [moons]
  (lazy-seq (cons
              moons
              (steps (map
                       (fn [moon] (apply-velocity (reduce apply-gravity moon (remove #{moon} moons))))
                       moons)))))

(defn energy [moons]
  (let [abs (fn [i] (Math/abs i))
        one-moon-energy (fn [moon]
                          (*
                            (reduce + (map abs (:position moon)))
                            (reduce + (map abs (:velocity moon)))))]
    (reduce + (map one-moon-energy moons))))


(defn number-of-steps-to-previous-point [moons]
  (->>
    (steps moons)
    (map-indexed vector)
    (filter (fn [[_ current]] (= moons current)) )
    (map first)
    second))

(defn one-dimension-moon [dimension m]
  (moon [(nth (:position m) dimension)]))

; https://rosettacode.org/wiki/Least_common_multiple#Clojure
(defn gcd
  [a b]
  (if (zero? b)
    a
    (recur b, (mod a b))))

(defn lcm
  [a b]
  (/ (* a b) (gcd a b)))

(defn lcmv [& v] (reduce lcm v))

(defn part-2 [moons]
  (let [x-axis-moons (map (partial one-dimension-moon 0) moons)
        y-axis-moons (map (partial one-dimension-moon 1) moons)
        z-axis-moons (map (partial one-dimension-moon 2) moons)
        x-axis-steps (number-of-steps-to-previous-point x-axis-moons)
        y-axis-steps (number-of-steps-to-previous-point y-axis-moons)
        z-axis-steps (number-of-steps-to-previous-point z-axis-moons)]
    (lcmv x-axis-steps y-axis-steps z-axis-steps)))

(part-2 mymoons)

(deftest day12
  (testing "day12"
    (is (=
          (nth (steps [
                       (moon [-1 0 2])
                       (moon [2 -10 -7])
                       (moon [4 -8 8])
                       (moon [3 5 -1])])
               1)
          [(moon [2 -1 1] [3 -1 -1])
           (moon [3 -7 -4] [1 3 3])
           (moon [1 -7 5] [-3 1 -3])
           (moon [2 2 0] [-1 -3 1])]))
    (is (=
          (nth (steps [
                       (moon [-1 0 2])
                       (moon [2 -10 -7])
                       (moon [4 -8 8])
                       (moon [3 5 -1])
                       ])
               10)
          [
           (moon [2 1 -3] [-3 -2 1])
           (moon [1 -8 0] [-1 1 3])
           (moon [3 -6 1] [3 2 -3])
           (moon [2 0 4] [1 -1 -1])]))
    (is (=
          (nth
            (steps [
                    (moon [-8 -10 0])
                    (moon [5 5 10])
                    (moon [2 -7 3])
                    (moon [9 -8 -3])
                    ])
            100)
          [(moon [8 -12 -9] [-7 3 0])
           (moon [13 16 -3] [3 -11 -5])
           (moon [-29 -11 -1] [-3 7 4])
           (moon [16 -13 23] [7 1 1])]))
    (is (=
          (energy [(moon [8 -12 -9] [-7 3 0])
                   (moon [13 16 -3] [3 -11 -5])
                   (moon [-29 -11 -1] [-3 7 4])
                   (moon [16 -13 23] [7 1 1])])
          1940
          ))

    (is (=
          (part-2 [(moon [-1 0 2]) (moon [2 -10 -7]) (moon [4 -8 8]) (moon [3 5 -1])])
          2772
          ))

    (is (=
          (part-2 [
                   (moon [-8 -10 0])
                   (moon [5 5 10])
                   (moon [2 -7 3])
                   (moon [9 -8 -3])
                   ])
          4686774924
          ))
    ))

(run-tests)

;part-1
(energy
  (nth
    (steps (parse-input "<x=-2, y=9, z=-5>\n<x=16, y=19, z=9>\n<x=0, y=3, z=6>\n<x=11, y=0, z=11>"))
    1000))

;part-2
(part-2 (parse-input "<x=-2, y=9, z=-5>\n<x=16, y=19, z=9>\n<x=0, y=3, z=6>\n<x=11, y=0, z=11>"))