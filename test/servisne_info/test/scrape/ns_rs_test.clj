(ns servisne-info.test.scrape.ns-rs-test
  (:use clojure.test
        servisne-info.test.utils
        servisne-info.scrape.ns-rs))

(def links-page-fixture (load-cassette "links_page"))
(def info-page-fixture (load-cassette "info_page"))

(deftest info-links-url-test
  (is (= (info-links-url info-site) "http://www.021.rs/Novi-Sad/Servisne-informacije/")))

(deftest info-links-test
  (let [links (info-links info-site links-page-fixture)]
    (is (= (count links) 30))
    (is (= (first links) "http://www.021.rs/Novi-Sad/Servisne-informacije/Iskljucenja-struje-za-20-januar-2.html"))
    (is (= (last links) "http://www.021.rs/Novi-Sad/Servisne-informacije/Od-srede-ponovo-dacki-polasci.html"))))

(deftest path-to-url-test
  (is (= (path-to-url info-site "/Novi-Sad/Servisne-informacije/")
         "http://www.021.rs/Novi-Sad/Servisne-informacije/")))
