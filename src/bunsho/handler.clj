(ns bunsho.handler
  (:use compojure.core)
  (:use ring.util.response)
  (:use cheshire.core)
  (:use [clojure.tools.logging :only (info error)])
  (:use [ring.middleware.format-params :only [wrap-restful-params]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [net.cgrand.enlive-html :as enlive]
            [clojure.data.json :as json]))

(def tasks [{:name "Name 1" :price "200"}
            {:name "Name 2" :price "350"}])

(def tasks2 [{:name "Name 3" :price "500"}
            {:name "Name 4" :price "450"}])

(defn rows [items]
  (enlive/html 
   (map-indexed
    #(identity [:tr [:td (+ 1 %1)] [:td (:name %2)] [:td (:price %2)]]) items)))

(defn total [items]
  (reduce + (map #(read-string (:price %)) items)))

(defn total-row [items]
  (enlive/html [:tr [:td {:colspan "2"}] [:td (total items)]]))

(enlive/deftemplate layout "layout.html" [title content]
  [#{:title :h1}] (enlive/content title)
  [:table.table :tbody] (enlive/content (rows content))
  [:table.table :tbody] (enlive/append (total-row content)))

(defroutes app-routes
  (GET "/" [] (layout "Front page 2k" tasks))
  (POST "/pop" {body-params :body-params} (layout "Ala ma kota" body-params))
  (route/resources "/")
  (route/not-found "Not Found"))

(defn inspect-params [app]
  (fn [request]
    (info (:params request) (:json-params request))
    (app request)))

(def app
  (-> (handler/api app-routes)
      (inspect-params)
      (wrap-restful-params :formats [:json-kw])))
