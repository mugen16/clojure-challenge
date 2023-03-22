(ns invoice-spec
  (:require [clojure.data.json :as json]
            [clojure.spec.alpha :as s]
            [clojure.string :as string]))

(s/def :customer/name string?)
(s/def :customer/email string?)
(s/def :invoice/customer (s/keys :req [:customer/name
                                       :customer/email]))

(s/def :tax/rate double?)
(s/def :tax/category #{:iva})
(s/def ::tax (s/keys :req [:tax/category
                           :tax/rate]))
(s/def :invoice-item/taxes (s/coll-of ::tax :kind vector? :min-count 1))

(s/def :invoice-item/price double?)
(s/def :invoice-item/quantity double?)
(s/def :invoice-item/sku string?)

(s/def ::invoice-item
  (s/keys :req [:invoice-item/price
                :invoice-item/quantity
                :invoice-item/sku
                :invoice-item/taxes]))

(s/def :invoice/issue-date inst?)
(s/def :invoice/items (s/coll-of ::invoice-item :kind vector? :min-count 1))

(s/def ::invoice
  (s/keys :req [:invoice/issue-date
                :invoice/customer
                :invoice/items]))

(def key-map-namespace {:issue_date "invoice"
                        :customer "invoice"
                        :items "invoice"
                        :order_reference "invoice"
                        :payment_date "invoice"
                        :payment_means "invoice"
                        :payment_means_type "invoice"
                        :number "invoice"
                        :email "customer"
                        :rate "tax"
                        :category "tax"
                        :taxes "invoice-item"
                        :price "invoice-item"
                        :quantity "invoice-item"
                        :sku "invoice-item"
                        :tax_category "tax"
                        :tax_rate "tax"
                        :retentions "invoice"
                        :name "customer"})


(defn parse-date [str-date]
  (.parse (java.text.SimpleDateFormat. "dd/MM/yyyy") str-date))

(defn convert-value [key value]
  (if (int? value)
    (double value)
    (if (= key :invoice/issue-date)
      (parse-date value)
      (if (= key :tax/category)
        :iva
        value))))

(defn custom-keyword [key]
  (if (= key "invoice")
    (keyword key)
    (if (= key "tax_category")
      (keyword (get key-map-namespace (keyword "category")) "category")
      (if (= key "tax_rate")
        (keyword (get key-map-namespace (keyword "rate")) "rate")
        (if (= key "company_name")
          (keyword (get key-map-namespace (keyword "name")) "name")
          (keyword (get key-map-namespace (keyword key) "") (string/replace (name key) "_" "-")))))))

(defn read-invoice [file-name]
  (let [json-str (slurp file-name)
        invoice-map (json/read-str json-str :key-fn custom-keyword :value-fn convert-value)
        data (get invoice-map :invoice)]
    (s/explain ::invoice data)
    (if (s/valid? ::invoice data)
      true
      false)))

(let [result (read-invoice "E:\\Data\\alex\\Dev\\clojure-challenge\\invoice.json")]
  (println result))
