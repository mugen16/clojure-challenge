(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(find-invoice-items invoice)
;{:invoice-item/id  "ii3"
;                  :invoice-item/sku "SKU 3"
;                  :taxable/taxes    [{:tax/id       "t3"
;                                      :tax/category :iva
;                                      :tax/rate     19}]}
; {:invoice-item/id          "ii4"
;                  :invoice-item/sku         "SKU 3"
;                  :retentionable/retentions [{:retention/id       "r2"
;                                              :retention/category :ret_fuente
;                                              :retention/rate     1}]}
