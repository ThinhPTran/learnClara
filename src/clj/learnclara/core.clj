(ns learnclara.core
  (:require [clara.rules :refer :all]))

(defrecord SupportRequest [client level])
(defrecord ClientRepresentative [name client])

(defrule is-important
  "Find important support requests."
  [SupportRequest (= :high level)]
  =>
  (println "High support request!"))

(defrule notify-client-rep
  "Find the client representativ and send a notification of a support request."
  [SupportRequest (= ?client client)]
  [ClientRepresentative (= ?client client) (= ?name name)] ;Join via the ?client binding
  =>
  (println "Notify" ?name "that" ?client "has a new support request!"))

(defrule notify-client-rep-on-high
  "Find the client representativ and send a notification of a high level request."
  [SupportRequest (= ?client client) (= :high level)]
  [ClientRepresentative (= ?client client) (= ?name name)]
  =>
  (println "Nofify" ?name "that" ?client "has a high level request!"))

(defrule notify-client-rep-on-low
  "Find the client representativ and send a notification of a low level request."
  [SupportRequest (= ?client client) (= :low level)]
  [ClientRepresentative (= ?client client) (= ?name name)]
  =>
  (println "Nofify" ?name "that" ?client "has a low level request!"))

;; Run the rules! We can just use Clojure's threading macro to wire thing up.
(-> (mk-session)
    (insert (->ClientRepresentative "Alice" "Acme")
            (->SupportRequest "Acme" :high)
            (->ClientRepresentative "Thinh" "Appsmith")
            (->SupportRequest "Appsmith" :low))
    (fire-rules))











