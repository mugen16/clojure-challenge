(ns invoice-item-test
  (:require [clojure.test :refer :all]
            [invoice-item :as ii]))

(deftest test-subtotal-without-discount
  (is (= 100 (ii/subtotal {:precise-quantity 2.0 :precise-price 50.0 }))))

(deftest test-subtotal-with-discount
  (is (= 80.0 (ii/subtotal {:precise-quantity 2 :precise-price 50.0 :discount-rate 20.0}))))

(deftest test-subtotal-with-zero-discount
  (is (= 100.0 (ii/subtotal {:precise-quantity 2 :precise-price 50.0 :discount-rate 0.0}))))

(deftest test-subtotal-with-quantity-of-one
  (is (= 50.0 (ii/subtotal {:precise-quantity 1 :precise-price 50.0}))))

(deftest test-subtotal-with-price-of-zero
  (is (= 0.0 (ii/subtotal {:precise-quantity 2 :precise-price 0.0}))))

(deftest test-subtotal-with-zero-quantity
  (is (= 0 (ii/subtotal {:precise-quantity 0, :precise-price 100}))))

(deftest test-subtotal-with-zero-quantity-and-price
  (is (= 0 (ii/subtotal {:precise-quantity 0, :precise-price 0}))))