(ns clojurebot.eval
  (:require [clojurebot.sandbox :as sb]
            [clojure.tools.logging :as log]
            [ring.middleware.params :refer [wrap-params]]))

(defn handler* [{{:strs [expression befuddled]} :params}]
  (try
    (let [[stdout stderr result] (sb/eval-message expression befuddled)]
      {:status 200
       :body (pr-str {:stdout stdout
                      :stderr stderr
                      :result result})})
    (catch Throwable t
      (log/error t "error evaluating" expression)
      (throw t))))

(def handler (-> #'handler*
                 wrap-params))
