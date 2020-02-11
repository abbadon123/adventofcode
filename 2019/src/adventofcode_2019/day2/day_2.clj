(ns adventofcode-2019.day2.day-2
  (:require [clojure.test :refer :all]))


(defn computer [memory]
  {:memory              memory
   :instruction-pointer 0})

(defn address-value [computer n]
  (let [memory (:memory computer)
        instruction-pointer (:instruction-pointer computer)
        address-n (+ n instruction-pointer)]
    (memory address-n)))

(defn operation [computer]
  (let [instruction (address-value computer 0)
        operation (case instruction
                    1 +
                    2 *)]
    operation))

(defn parameter-n [computer n]
  (let [address (address-value computer n)
        memory (:memory computer)
        value (memory address)]
    value))

(defn parameter-1 [computer]
  (parameter-n computer 1))

(defn parameter-2 [computer]
  (parameter-n computer 2))

(defn result-address [computer]
  (address-value computer 3))

(defn execute [computer]
  (let [parameter-1 (parameter-1 computer)
        parameter-2 (parameter-2 computer)
        operation (operation computer)
        result (operation parameter-1 parameter-2)
        result-adr (result-address computer)
        update-memory (fn [memory] (assoc memory result-adr result))]
    (->
      computer
      (update-in [:memory] update-memory)
      (update-in [:instruction-pointer] + 4))))

(defn halt? [computer]
  (let [instruction (address-value computer 0)]
    (not (or (= instruction 1)
             (= instruction 2)))))

(defn run-program [memory]
  (loop [computer (computer memory)]
    (if (halt? computer)
      (:memory computer)
      (recur (execute computer)))))

(deftest a-test
  (testing "small programs"
    (is (= (run-program [1, 0, 0, 0, 99]) [2, 0, 0, 0, 99]))
    (is (= (run-program [2, 3, 0, 3, 99]) [2, 3, 0, 6, 99]))
    (is (= (run-program [2, 4, 4, 5, 99, 0]) [2, 4, 4, 5, 99, 9801]))
    (is (= (run-program [1, 1, 1, 4, 99, 5, 6, 0, 99]) [30, 1, 1, 4, 2, 5, 6, 0, 99]))
    ))


(run-tests)

(defn run [noun verb]
  (->
    [1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 1, 9, 19, 1, 19, 5, 23, 2, 6, 23, 27, 1, 6, 27, 31, 2, 31, 9, 35, 1, 35, 6, 39, 1, 10, 39, 43, 2, 9, 43, 47, 1, 5, 47, 51, 2, 51, 6, 55, 1, 5, 55, 59, 2, 13, 59, 63, 1, 63, 5, 67, 2, 67, 13, 71, 1, 71, 9, 75, 1, 75, 6, 79, 2, 79, 6, 83, 1, 83, 5, 87, 2, 87, 9, 91, 2, 9, 91, 95, 1, 5, 95, 99, 2, 99, 13, 103, 1, 103, 5, 107, 1, 2, 107, 111, 1, 111, 5, 0, 99, 2, 14, 0, 0]
    (assoc 1 noun)
    (assoc 2 verb)
    run-program
    first))

(run 12 2)

(let [match? (fn [[x y]] (= (run x y) 19690720))
      pairs (for [noun (range 0 100) verb (range 0 100)] [noun verb])
      matches (filter match? pairs)
      match (first matches)
      [noun verb] match]
  (+ (* 100 noun) verb))
