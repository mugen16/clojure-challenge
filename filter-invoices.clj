(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(defn filter-invoice-items [invoice]
      (->> invoice
           :invoice/items
           (filter (fn [param1]
                       (or (some #(= 19 (:tax/rate %)) (:taxable/taxes param1))
                           (some #(= 1 (:retention/rate %)) (:retentionable/retentions param1)))))
           (filter (fn [param1]
                       (not (some #(= 19 (:tax/rate %)) (:taxable/taxes param1))
                            (some #(= 1 (:retention/rate %)) (:retentionable/retentions param1)))))))


(filter-invoice-items invoice)

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