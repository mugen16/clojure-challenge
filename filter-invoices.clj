(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(defn get-invoice-items [invoice]
  (->> (:invoice/items invoice)
       (filter (fn [item]
                 (let [has-iva-19? (-> item :taxable/taxes
                                       (some #(and (= (:tax/category %) :iva)
                                                   (= (:tax/rate %) 19))))
                       has-ret-1? (-> item :retentionable/retations
                                      (some #(and (= (:retention/category %) :ret_fuente)
                                                  (= (:retention/rate %) 1))))]
                   )))))