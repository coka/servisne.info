(ns servisne-info.tasks
  (:require [overtone.at-at :as at-at]
            [servisne-info.tasks.scrape :refer [scrape-task]]
            [servisne-info.tasks.send-notifications :refer [send-notifications-task]]
            [servisne-info.utils :refer [production?]]))

(def tasks-pool (at-at/mk-pool :cpu-count 1))
(def default-period (* 60 60 1000)) ; one hour
(def periodic-tasks (atom []))

(defn add-periodic-task [task]
  (swap! periodic-tasks conj task))

(defn schedule-periodic-tasks []
  (doseq [task @periodic-tasks]
    (at-at/every default-period task tasks-pool :initial-delay (/ default-period 10))))

(if production?
  (do
    (add-periodic-task scrape-task)
    (add-periodic-task send-notifications-task)))
