(ns adventofcode-2019.IntCode
  (:require [clojure.test :refer :all]))

(defn computer [memory input]
  {:memory              memory
   :input               (if (coll? input) input (vector input))
   :output              []
   :instruction-pointer 0
   :halt                false})

(defn address-value [computer n]
  (let [memory (:memory computer)
        instruction-pointer (:instruction-pointer computer)
        address-n (+ n instruction-pointer)]
    (memory address-n)))

(defn instruction-code [computer]
  (address-value computer 0))

(defn parameter-n [computer n mode]
  (let [address (address-value computer n)
        value (case mode
                0 (-> computer (:memory) (get address))
                1 address)]
    value))

(defn ABCDE [code]
  (let [digits (map #(Character/digit % 10) (str code))
        digits-count (count digits)
        zero-padding (repeat (- 5 digits-count) 0)]
    (concat zero-padding digits)))

(defn parameter-1-mode [computer]
  (let [code (instruction-code computer)
        [_ _ C _ _] (ABCDE code)]
    C))

(defn parameter-2-mode [computer]
  (let [data (address-value computer 0)
        [_ B _ _ _] (ABCDE data)]
    B))

(defn parameter-1 [computer]
  (parameter-n computer 1 (parameter-1-mode computer)))

(defn parameter-2 [computer]
  (parameter-n computer 2 (parameter-2-mode computer)))

(defn update-memory [computer address value]
  (update-in computer [:memory] (fn [memory] (assoc memory address value))))

(defn update-output [computer value]
  (update-in computer [:output] (fn [output] (conj output value))))

(defn move-instruction-pointer [computer count]
  (update-in computer [:instruction-pointer] + count))

(defn set-instruction-pointer [computer ip]
  (assoc-in computer [:instruction-pointer] ip))

(defn operation-add [computer]
  (let [parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)
        result (+ parameter-1 parameter-2)
        result-adr (address-value computer 3)]
    (->
      computer
      (update-memory result-adr result)
      (move-instruction-pointer 4))))

(defn operation-multiply [computer]
  (let [
        parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)
        result (* parameter-1 parameter-2)
        result-adr (address-value computer 3)]
    (->
      computer
      (update-memory result-adr result)
      (move-instruction-pointer 4))))

(defn next-input [computer]
  (first (:input computer)))

(defn drop-next-input [computer]
  (update computer :input rest))

(defn operation-input [computer]
  (let [
        input (next-input computer)
        result-adr (address-value computer 1)]
    (->
      computer
      drop-next-input
      (update-memory result-adr input)
      (move-instruction-pointer 2))))

(defn operation-output [computer]
  (let [parameter-1 (parameter-1 computer)]
    (->
      computer
      (update-output parameter-1)
      (move-instruction-pointer 2))))

(defn operation-jump-if-true [computer]
  (let [parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)]
    (if (not= 0 parameter-1)
      (set-instruction-pointer computer parameter-2)
      (move-instruction-pointer computer 3))))

(defn operation-jump-if-false [computer]
  (let [parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)]
    (if (= 0 parameter-1)
      (set-instruction-pointer computer parameter-2)
      (move-instruction-pointer computer 3))))

(defn operation-less-than [computer]
  (let [parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)
        equal (< parameter-1 parameter-2)
        result (if equal 1 0)
        result-adr (address-value computer 3)]
    (->
      computer
      (update-memory result-adr result)
      (move-instruction-pointer 4))))

(defn operation-equals [computer]
  (let [parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)
        equal (= parameter-1 parameter-2)
        result (if equal 1 0)
        result-adr (address-value computer 3)]
    (->
      computer
      (update-memory result-adr result)
      (move-instruction-pointer 4))))

(defn opcode->instruction [opcode]
  (case opcode
    1 operation-add
    2 operation-multiply
    3 operation-input
    4 operation-output
    5 operation-jump-if-true
    6 operation-jump-if-false
    7 operation-less-than
    8 operation-equals
    99 (fn [computer] (assoc computer :halt true))))

(defn opcode [computer]
  (mod (instruction-code computer) 100))

(defn debug [computer]
  (assoc computer :indexed-memory (map-indexed vector (computer :memory))))

(defn execute [computer]
  (let [opcode (opcode computer)
        instruction (opcode->instruction opcode)]
    (instruction computer)))

(defn halt [computer]
  (or
    (:halt computer)
    (not-empty (:output computer))))

(defn run-computer [computer]
  (loop [computer computer]
    (if (halt computer)
      computer
      (recur (execute computer)))))

(defn run-program [memory]
  (->
    (computer memory nil)
    run-computer
    :memory))

(def ZERO 0)
(def NOT-ZERO 1)
(def EIGHT 8)
(def NOT-EIGHT 1)
(def LOWER-THEN-EIGHT 7)
(def GREATER-THEN-EIGHT 9)

(deftest a-test
  (testing "small programs"
    (is (= (run-program [1, 0, 0, 0, 99]) [2, 0, 0, 0, 99]))
    (is (= (run-program [2, 3, 0, 3, 99]) [2, 3, 0, 6, 99]))
    (is (= (run-program [2, 4, 4, 5, 99, 0]) [2, 4, 4, 5, 99, 9801]))
    (is (= (run-program [1, 1, 1, 4, 99, 5, 6, 0, 99]) [30, 1, 1, 4, 2, 5, 6, 0, 99]))

    ;; if 8 then 1
    (is (=
          (:output (run-computer (computer [3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8] NOT-EIGHT)))
          [0]))
    (is (=
          (:output (run-computer (computer [3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8] EIGHT)))
          [1]))

    ;; if < 8 then 1
    (is (=
          (:output (run-computer (computer [3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8] LOWER-THEN-EIGHT)))
          [1]))
    (is (=
          (:output (run-computer (computer [3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8] EIGHT)))
          [0]))
    (is (=
          (:output (run-computer (computer [3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8] GREATER-THEN-EIGHT)))
          [0]))

    ;; if 8 then 1
    (is (=
          (:output (run-computer (computer [3, 3, 1108, -1, 8, 3, 4, 3, 99] EIGHT)))
          [1]))
    (is (=
          (:output (run-computer (computer [3, 3, 1108, -1, 8, 3, 4, 3, 99] NOT-EIGHT)))
          [0]))

    ;; if < 8 then 1
    (is (=
          (:output (run-computer (computer [3, 3, 1107, -1, 8, 3, 4, 3, 99] LOWER-THEN-EIGHT)))
          [1]))
    (is (=
          (:output (run-computer (computer [3, 3, 1107, -1, 8, 3, 4, 3, 99] EIGHT)))
          [0]))
    (is (=
          (:output (run-computer (computer [3, 3, 1107, -1, 8, 3, 4, 3, 99] GREATER-THEN-EIGHT)))
          [0]))

    ;; if 0 then 0
    (is (=
          (:output (run-computer (computer [3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9] ZERO)))
          [0]))
    (is (=
          (:output (run-computer (computer [3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9] NOT-ZERO)))
          [1]))

    ;; if 0 then 0
    (is (=
          (:output (run-computer (computer [3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1] ZERO)))
          [0]))

    (is (=
          (:output (run-computer (computer [3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1] NOT-ZERO)))
          [1]))
    ))

(run-tests)


(use 'clojure.tools.trace)
(untrace-ns adventofcode-2019.IntCode)


