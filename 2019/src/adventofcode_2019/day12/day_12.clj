(ns adventofcode-2019.day12.day-12
  (:require [clojure.test :refer :all]))

(defn moon
  ([position velocity] {
                        :position {
                                   :x (nth position 0)
                                   :y (nth position 1)
                                   :z (nth position 2)
                                   }
                        :velocity {
                                   :x (nth velocity 0)
                                   :y (nth velocity 1)
                                   :z (nth velocity 2)
                                   }
                        })
  ([position] (moon position (repeat 0)))
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
      :velocity {
                 :x (+ (-> moon :velocity :x) (one-dimension-gravity-change (-> moon :position :x) (-> other :position :x)))
                 :y (+ (-> moon :velocity :y) (one-dimension-gravity-change (-> moon :position :y) (-> other :position :y)))
                 :z (+ (-> moon :velocity :z) (one-dimension-gravity-change (-> moon :position :z) (-> other :position :z)))
                 })))

(defn apply-velocity [moon]
  (assoc moon :position (merge-with + (:position moon) (:velocity moon))))

(defn steps [moons]
  (lazy-seq (cons
              moons
              (steps (map
                       (fn [moon] (apply-velocity (reduce apply-gravity (cons moon (remove #{moon} moons)))))
                       moons)))))

(defn energy [moons]
  (let [abs (fn [i] (Math/abs i))
        one-moon-energy (fn [moon]
                          (*
                            (apply + (map abs (-> moon :position vals)))
                            (apply + (map abs (-> moon :velocity vals)))))]
    (apply + (map one-moon-energy moons))))

(deftest day12
  (testing "day12"
    (is (=
          (nth (steps [
                       (moon [-1 0 2])
                       (moon [2 -10 -7])
                       (moon [4 -8 8])
                       (moon [3 5 -1])
                       ])
               1)
          [
           (moon [2 -1 1] [3 -1 -1])
           (moon [3 -7 -4] [1 3 3])
           (moon [1 -7 5] [-3 1 -3])
           (moon [2 2 0] [-1 -3 1])
           ]))
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
    ))

(run-tests)


(energy
  (nth
    (steps (parse-input "<x=-2, y=9, z=-5>\n<x=16, y=19, z=9>\n<x=0, y=3, z=6>\n<x=11, y=0, z=11>"))
    1000)
  )