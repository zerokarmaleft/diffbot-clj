(ns diffbot.core
  (:require [clj-http.client :as client]
            [clojure.xml :as xml])
  (:use clojure.java.io
        clojure.pprint
	clojure.data.json))

(def ^:dynamic *base-url* "http://www.diffbot.com/api/")

(defn api-action [method path & [opts]]
  (client/request
    (merge {:method method :url (str *base-url* path)} opts)))

(defn read-dml [response]
  (xml/parse (input-stream (.getBytes response "UTF-8"))))

(defn analyze [url token]
  (let [path (str "article?token=" token "&url=" url)]
    (read-json (:body (api-action :get path)))))

(defn front-page [url token]
  (let [path (str "frontpage?token=" token "&url=" url)]
    (read-dml (:body (api-action :get path)))))

;;(client/request
;; (merge {:method method :url (str api-base path)} opts))
