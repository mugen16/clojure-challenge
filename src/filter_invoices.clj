(ns filter-invoices
  (:require [clojure.edn :as edn]))

(def invoice (edn/read-string (slurp "E:\\Data\\alex\\Dev\\clojure-challenge\\invoice.edn")))

(defn tax-iva? [param]
  (some #(= 19 (:tax/rate %)) (:taxable/taxes param)))

(defn retention-rate? [param]
  (some #(= 1 (:retention/rate %)) (:retentionable/retentions param)))

(defn filter-invoice-items [invoice]
  (->> invoice
       :invoice/items
       (filter #(or (tax-iva? %) (retention-rate? %)))
       (filter #(not= (tax-iva? %) (retention-rate? %)))))

(let [result (filter-invoice-items invoice)]
  (print result))

;; Expected result
;; {:invoice-item/id  "ii3"
;;                  :invoice-item/sku "SKU 3"
;;                 :taxable/taxes    [{:tax/id       "t3"
;;                                      :tax/category :iva
;;                                      :tax/rate     19}]}
;;                 {:invoice-item/id          "ii4"
;;                  :invoice-item/sku         "SKU 3"
;;                  :retentionable/retentions [{:retention/id       "r2"
;;                                              :retention/category :ret_fuente
;;                                              :retention/rate     1}]}