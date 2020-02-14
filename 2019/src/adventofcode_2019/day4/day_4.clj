(ns adventofcode-2019.day4.day-4
  (:require [clojure.test :refer :all]))

(defn six-digit? [number]
  (= 6 (count (str number))))

(defn has-double? [number]
  (let [
        pairs (partition 2 1 (str number))
        pair-equal? (partial apply =)
        is-double (map pair-equal? pairs)]
    (true? (some identity is-double))))

(defn never-decrease? [number]
  (let [coll (seq (str number))]
    (= coll (sort coll))))

(defn valid? [number]
  (and
    (six-digit? number)
    (has-double? number)
    (never-decrease? number)))

(defn has-double2? [number]
  (let [
        coll (str number)
        pad (repeat 2 nil)
        coll-padded (concat pad coll pad)
        pairs (partition 4 1 coll-padded)
        pair-equal? (fn [[a b c d]]
                      (and
                        (not= a b)
                        (= b c)
                        (not= c d)))
        is-double (map pair-equal? pairs)]
    (true? (some identity is-double))))

(defn valid2? [number]
  (and
    (six-digit? number)
    (has-double2? number)
    (never-decrease? number)))

(deftest a-test
  (testing "valid?"
    (is (= (valid? 122345) true))
    (is (= (valid? 111123) true))
    (is (= (valid? 135679) false))
    (is (= (valid? 223450) false))
    (is (= (valid2? 112233) true))
    (is (= (valid2? 123444) false))
    (is (= (valid2? 111122) true))
    )
  )
(run-tests)

(->>
  (range 248345 746315)
  (filter valid?)
  count)

(->>
  (range 248345 746315)
  (filter valid2?)
  count)

