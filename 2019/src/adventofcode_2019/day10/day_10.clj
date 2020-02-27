(ns adventofcode-2019.day10.day-10
  (:require [clojure.test :refer :all]
            [clojure.string :as str]))

(def make-two-point-function
  (memoize
    (fn [[x1 y1] [x2 y2]]
      (let [a (/ (- y2 y1) (- x2 x1))
            b (/ (- (* y1 x2) (* y2 x1)) (- x2 x1))]
        (fn [x] (+ (* a x) b))))))

(defn line [P1 P2] [P1 P2])
(defn P1 [line]
  (first line))
(defn P2 [line]
  (second line))
(defn x [point]
  (first point))
(defn y [point]
  (second point))
(defn x1 [line]
  (x (P1 line)))
(defn x2 [line]
  (x (P2 line)))
(defn y1 [line]
  (y (P1 line)))
(defn y2 [line]
  (y (P2 line)))

(defn vertical? [line]
  (== (x1 line) (x2 line)))

(defn in-range? [line X]
  (let [min-x (min (x1 line) (x2 line))
        max-x (max (x1 line) (x2 line))
        min-y (min (y1 line) (y2 line))
        max-y (max (y1 line) (y2 line))]
    (and
      (<= min-x (first X) max-x)
      (<= min-y (second X) max-y))))

(defn point-is-on-a-line [line X]
  (cond
    (not (in-range? line X)) false
    (or
      (= (P1 line) X)
      (= (P2 line) X)) false
    (vertical? line) true
    :default
    (let [f (make-two-point-function (P1 line) (P2 line))
          y (f (first X))]
      (== y (second X)))))


(defn visible [from to point]
  (let [line (line from to)]
    (not (point-is-on-a-line line point))))

(defn part-1 [asteroids]
  (println asteroids)
  (let [is-visible (fn [from to]
                     (every? (partial visible from to) asteroids))]
    (println "part-1=" (apply max-key first
                              (for [from asteroids]
                                (reverse [from (count (filter (partial is-visible from) asteroids))])
                                )))))

(part-1 [
         [1 0] [4 0]
         [0 2] [1 2] [2 2] [3 2] [4 2]
         [4 3]
         [3 4] [4 4]])

(def part-1-input
  (let [inputstr ".###..#......###..#...#
#.#..#.##..###..#...#.#
#.#.#.##.#..##.#.###.##
.#..#...####.#.##..##..
#.###.#.####.##.#######
..#######..##..##.#.###
.##.#...##.##.####..###
....####.####.#########
#.########.#...##.####.
.#.#..#.#.#.#.##.###.##
#..#.#..##...#..#.####.
.###.#.#...###....###..
###..#.###..###.#.###.#
...###.##.#.##.#...#..#
#......#.#.##..#...#.#.
###.##.#..##...#..#.#.#
###..###..##.##..##.###
###.###.####....######.
.###.#####.#.#.#.#####.
##.#.###.###.##.##..##.
##.#..#..#..#.####.#.#.
.#.#.#.##.##########..#
#####.##......#.#.####."
        lines (str/split-lines inputstr)
        height (count lines)
        width (count (first lines))
        asteroids (for [x (range width)
                        y (range height)
                        :when (= "#" (-> lines (get x) (get y) str))]
                    [x y])]
    asteroids))

(part-1 part-1-input)


(deftest day10-test
  (testing "day10"
    (is (==
          ((make-two-point-function [0 0] [2 2]) 3)
          3))
    (is (==
          ((make-two-point-function [0 0] [1 0]) 3)
          0))
    (is (==
          ((make-two-point-function [0 0] [3 4]) 0)
          0))
    (is (==
          ((make-two-point-function [0 0] [4 0]) 2)
          0))

    (is (==
          ((make-two-point-function [0 0] [10 0]) 100)
          0))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [1 1])
          true))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [3 3])
          true))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [0 0])
          false))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [10 10])
          false))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [0 10])
          false))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [10 0])
          false))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [11 11])
          false))
    (is (=
          (point-is-on-a-line (line [0 0] [10 10]) [-1 -1])
          false))
    (is (=
          (point-is-on-a-line (line [2 0] [2 2]) [2 1])
          true))
    (is (=
          (point-is-on-a-line (line [2 2] [0 2]) [1 2])
          true))
    (is (=
          (point-is-on-a-line (line [2 2] [1 2]) [1 2])
          false))
    ))

(run-tests)
