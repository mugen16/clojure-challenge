(ns invoice-spec
  (:require
   [clojure.spec.alpha :as s]
   [cheshire.core :as json]))

(s/def ::tax-category keyword?)
(s/def ::tax-rate float?)
(s/def ::taxes (s/keys :req-un [::tax-category ::tax-rate]))
(s/def ::customer (s/keys :req-un [::company-name ::email]))
(s/def ::items (s/keys :req-un [::price ::quantity ::sku ::taxes]))
(s/def ::retentions (s/keys :req-un [::tax-category ::tax-rate]))
(s/def ::invoice (s/keys :req-un [::issue-date ::order-reference ::payment-date
                                  ::payment-means ::payment-means-type ::number
                                  ::customer ::items ::retentions]))

(defn convert-invoice [filename]
  (let [json-str (slurp filename)
        invoice-map (json/parse-string json-str true)
        conform-result (s/conform ::invoice invoice-map)]
    (if (s/valid? ::invoice conform-result)
      conform-result
      nil)))

(convert-invoice "E:\\Data\\alex\\Dev\\clojure-challenge\\invoice.json")