(ns invoice-spec
  (:require [clojure.data.json :as json]
            [clojure.spec.alpha :as s]))

(s/def :customer/company-name string?)
(s/def :customer/email string?)
(s/def :invoice/customer (s/keys :req [:customer/company-name
                                       :customer/email]))

(s/def :tax/tax-rate double?)
(s/def :tax/tax-category #{:iva})
(s/def ::tax (s/keys :req [:tax/tax-category
                           :tax/tax-rate]))

(s/def :invoice-item/taxes (s/coll-of ::tax :kind vector? :min-count 1))

(s/def :invoice-item/price double?)
(s/def :invoice-item/quantity double?)
(s/def :invoice-item/sku string?)

(s/def ::invoice-item
  (s/keys :req [:invoice-item/price
                :invoice-item/quantity
                :invoice-item/sku
                :invoice-item/taxes]))

(s/def ::retention (s/keys :req [:tax/tax-category
                                 :tax/tax-rate]))

(s/def :invoice/issue-date inst?)
(s/def :invoice/order-reference string?)
(s/def :invoice/payment-date string?)
(s/def :invoice/payment-means string?)
(s/def :invoice/payment-means-type string?)
(s/def :invoice/number double?)

(s/def :invoice/items (s/coll-of ::invoice-item :kind vector? :min-count 1))
(s/def :invoice/retentions (s/coll-of ::retention :kind vector? :min-count 1))

(s/def ::invoice
  (s/keys :req [:invoice/issue-date
                :invoice/customer
                :invoice/items
                :invoice/retentions
                :invoice/order-reference
                :invoice/payment-date
                :invoice/payment-means
                :invoice/payment-means-type
                :invoice/number]))


(defn read-invoice [filename]
  (let [json-str (slurp filename)
        invoice (json/read-str json-str)]
    (print json-str)
    (print invoice)
    (if (s/valid? ::invoice invoice)
      invoice
      nil)))

(let [invoice (read-invoice "E:\\Data\\alex\\Dev\\clojure-challenge\\invoice.json")]
  (println invoice))
