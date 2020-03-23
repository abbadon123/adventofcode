(ns adventofcode-2019.day11.day-11
  (:require [clojure.test :refer :all]
            [clojure.data]
            [adventofcode-2019.IntCode :refer :all]))


(def input [3, 8, 1005, 8, 309, 1106, 0, 11, 0, 0, 0, 104, 1, 104, 0, 3, 8, 102, -1, 8, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 1001, 8, 0, 29, 3, 8, 102, -1, 8, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 0, 10, 4, 10, 102, 1, 8, 51, 3, 8, 102, -1, 8, 10, 1001, 10, 1, 10, 4, 10, 108, 0, 8, 10, 4, 10, 1002, 8, 1, 72, 1, 1104, 8, 10, 2, 1105, 15, 10, 2, 1106, 0, 10, 3, 8, 1002, 8, -1, 10, 1001, 10, 1, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 101, 0, 8, 107, 3, 8, 102, -1, 8, 10, 1001, 10, 1, 10, 4, 10, 108, 1, 8, 10, 4, 10, 101, 0, 8, 128, 2, 6, 8, 10, 3, 8, 102, -1, 8, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 0, 10, 4, 10, 102, 1, 8, 155, 1006, 0, 96, 2, 108, 10, 10, 1, 101, 4, 10, 3, 8, 1002, 8, -1, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 0, 10, 4, 10, 1002, 8, 1, 188, 2, 1, 5, 10, 3, 8, 102, -1, 8, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 0, 10, 4, 10, 102, 1, 8, 214, 2, 6, 18, 10, 1006, 0, 78, 1, 105, 1, 10, 3, 8, 1002, 8, -1, 10, 1001, 10, 1, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 102, 1, 8, 247, 2, 103, 8, 10, 2, 1002, 10, 10, 2, 106, 17, 10, 1, 1006, 15, 10, 3, 8, 102, -1, 8, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 101, 0, 8, 285, 1, 1101, 18, 10, 101, 1, 9, 9, 1007, 9, 992, 10, 1005, 10, 15, 99, 109, 631, 104, 0, 104, 1, 21102, 387507921664, 1, 1, 21102, 1, 326, 0, 1106, 0, 430, 21102, 932826591260, 1, 1, 21102, 337, 1, 0, 1106, 0, 430, 3, 10, 104, 0, 104, 1, 3, 10, 104, 0, 104, 0, 3, 10, 104, 0, 104, 1, 3, 10, 104, 0, 104, 1, 3, 10, 104, 0, 104, 0, 3, 10, 104, 0, 104, 1, 21101, 206400850983, 0, 1, 21101, 0, 384, 0, 1105, 1, 430, 21102, 3224464603, 1, 1, 21102, 395, 1, 0, 1106, 0, 430, 3, 10, 104, 0, 104, 0, 3, 10, 104, 0, 104, 0, 21102, 838433657700, 1, 1, 21102, 418, 1, 0, 1106, 0, 430, 21101, 825012007272, 0, 1, 21101, 429, 0, 0, 1106, 0, 430, 99, 109, 2, 21202, -1, 1, 1, 21101, 40, 0, 2, 21101, 461, 0, 3, 21102, 1, 451, 0, 1105, 1, 494, 109, -2, 2105, 1, 0, 0, 1, 0, 0, 1, 109, 2, 3, 10, 204, -1, 1001, 456, 457, 472, 4, 0, 1001, 456, 1, 456, 108, 4, 456, 10, 1006, 10, 488, 1102, 1, 0, 456, 109, -2, 2106, 0, 0, 0, 109, 4, 1202, -1, 1, 493, 1207, -3, 0, 10, 1006, 10, 511, 21101, 0, 0, -3, 21202, -3, 1, 1, 21201, -2, 0, 2, 21102, 1, 1, 3, 21102, 1, 530, 0, 1106, 0, 535, 109, -4, 2106, 0, 0, 109, 5, 1207, -3, 1, 10, 1006, 10, 558, 2207, -4, -2, 10, 1006, 10, 558, 22101, 0, -4, -4, 1106, 0, 626, 22102, 1, -4, 1, 21201, -3, -1, 2, 21202, -2, 2, 3, 21101, 0, 577, 0, 1106, 0, 535, 22102, 1, 1, -4, 21101, 1, 0, -1, 2207, -4, -2, 10, 1006, 10, 596, 21102, 0, 1, -1, 22202, -2, -1, -2, 2107, 0, -3, 10, 1006, 10, 618, 21201, -1, 0, 1, 21102, 618, 1, 0, 105, 1, 493, 21202, -2, -1, -2, 22201, -4, -2, -4, 109, -5, 2105, 1, 0])

(def black 0)
(def white 1)

(def up :up)
(def down :down)
(def left :left)
(def right :right)


(defn facing->scalar [facing]
  (cond
    (= facing up) [0 1]
    (= facing down) [0 -1]
    (= facing left) [-1 0]
    (= facing right) [1 0]))

(defn color [robot]
  (let [robot-position (:position robot)
        panels (:panels robot)
        panel-colors (get panels robot-position [black])
        panel-color (last panel-colors)]
    panel-color))

(defn move [position facing]
  (into [] (map + position (facing->scalar facing))))

(defn do-turn [facing turn]
  (let [turn-left? (= turn 0)
        new-facing (cond
                     (= facing up) (if turn-left? left right)
                     (= facing down) (if turn-left? right left)
                     (= facing left) (if turn-left? down up)
                     (= facing right) (if turn-left? up down))]
    new-facing))

(defn part-1 [program]
  (loop [robot {
                :position [0 0]
                :facing   up
                :panels   {}
                :computer (computer (expand-memory program 1000) [])
                }]
    (let [computer-1 (run-computer (add-input (:computer robot) [(color robot)]))
          paint-color (take-output computer-1)
          computer-2 (run-computer (drop-output computer-1))
          turn (take-output computer-2)
          new-facing (do-turn (:facing robot) turn)
          new-position (move (:position robot) new-facing)]
      (if (:halt computer-2)
        (count (keys (:panels robot)))
        (recur
          {
           :facing   new-facing
           :position new-position
           :panels   (update (:panels robot) (:position robot) (fnil conj []) paint-color)
           :computer (drop-output computer-2)
           }
          )))))

; 1732
; (part-1 input)

(defn part-2 [program]
  (loop [robot {
                :position [0 0]
                :facing   up
                :panels   {[0 0] [white]}
                :computer (computer (expand-memory program 1000) [])
                }]
    (let [computer-1 (run-computer (add-input (:computer robot) [(color robot)]))
          paint-color (take-output computer-1)
          computer-2 (run-computer (drop-output computer-1))
          turn (take-output computer-2)
          new-facing (do-turn (:facing robot) turn)
          new-position (move (:position robot) new-facing)]
      (if (:halt computer-2)
        (:panels robot)
        (recur
          {
           :facing   new-facing
           :position new-position
           :panels   (update (:panels robot) (:position robot) (fnil conj []) paint-color)
           :computer (drop-output computer-2)
           }
          )))))

(defn draw [panels]
  (let [coordinates (keys panels)
        min-x (apply min (map first coordinates))
        max-x (apply max (map first coordinates))
        min-y (apply min (map second coordinates))
        max-y (apply max (map second coordinates))]
    (println min-x max-x min-y max-y)
    (doseq [y (range max-y (dec min-y) -1)]
      (println
        (map
          (fn [x] (if (= [black] (get panels [x y])) " " "X"))
          (range min-x (+ 10 max-x)))
        ))))

(draw (part-2 input))