(ns sicp.chapter2.ch2-1
  (:require [clojure.contrib.generic.math-functions :as math])
  (:use (sicp.chapter1 ch1-1 ch1-2)))

(defn make-rat [n d] (list n d))

(defn numer [x] (first x))

(defn denom [x] (second x))

(defn add-rat [x y]
  (make-rat (+ (* (numer x) (denom y))
	       (* (numer y) (denom x)))
	    (* (denom x) (denom y))))

(defn sub-rat [x y]
  (make-rat (- (* (numer x) (denom y))
	       (* (numer y) (denom x)))
	    (* (denom x) (denom y))))

(defn mul-rat [x y]
  (make-rat (* (numer x) (numer y))
	    (* (denom x) (denom y))))

(defn div-rat [x y]
  (make-rat (* (numer x) (denom y))
	    (* (denom x) (numer y))))

(defn equal-rat? [x y]
  (= (* (numer x) (denom y))
     (* numer y) (denom x)))

;; Exercise 2.1
(defn make-normalized-rat [n d]
  (if (or (neg? n) (neg? d))
    (make-rat (- (abs n)) (abs d))
    (make-rat n d)))

;; Exercise 2.2
(defn make-segment [start-segment end-segment]
  (list start-segment end-segment))

(defn start-segment [segment]
  (first segment))

(defn end-segment [segment]
  (second segment))

(defn make-point [x-point y-point]
  (list x-point y-point))

(defn x-point [point]
  (first point))

(defn y-point [point]
  (second point))

(defn print-point [p]
  (println "\n(" (x-point p) "," (y-point p) ")"))



;; Exercise 2.3
(defn make-rectangle [bottom-left top-right]
  (let [segment (make-segment bottom-left top-right)
	x-length (+ (abs (x-point (start-segment segment)))
		   (abs (x-point (end-segment segment))))
	y-length (+ (abs (y-point (start-segment segment)))
		   (abs (y-point (end-segment segment))))]
    (list x-length y-length)))

(defn x-length [rectangle]
  (first rectangle))

(defn y-length [rectangle]
  (second rectangle))

(defn rectangle-perimeter [rectangle]
  (+ (* 2 (x-length rectangle)) (* 2 (y-length rectangle))))

(defn rectangle-area [rectangle]
  (* (x-length rectangle) (y-length rectangle)))

(defn make-rectangle-alt [bottom-left top-right]
  (make-segment bottom-left top-right))

(defn x-length-alt [rectangle]
  (+ (abs (x-point (start-segment rectangle)))
		   (abs (x-point (end-segment rectangle)))))

(defn y-length-alt [rectangle]
  (+ (abs (y-point (start-segment rectangle)))
		   (abs (y-point (end-segment rectangle)))))
	
	
  
 ;; Ex 2.4

(defn my-cons [x y]
     (fn [m] (m x y)))

(defn my-car [z]
     (z (fn [p q] p)))

(defn my-cdr [z]
     (z (fn [p q] q)))


;; Ex 2.5

(defn int-cons [x y]
  (* (fast-expt 2 x) (fast-expt 3 y)))

(defn- divide-until
  ([x even-or-odd? divisor]
     (loop [value x count 0]
       (if (or (= value 1) (even-or-odd? value))
	 count
	 (recur (/ value divisor) (inc count))))))

(def divide-until-odd #(divide-until % odd? 2))
(def divide-until-even #(divide-until % even? 3))

(defn int-car [x]
  (divide-until-odd x))

(defn int-cdr [x]
  (->> 1944
       divide-until-odd
       (fast-expt 2)
       (/ 1944)
       divide-until-even))

;; Ex 2.6

(def church-zero
     (fn [_] (fn [x] x)))

(defn add-1 [n]
  (fn [f]
    (fn [x]
      (f ((n f) x)))))

(def church-one
     (fn [f] (fn [x] (f x))))

(def church-two 
     (fn [f] (fn [x] (f (f x)))))

(defn church+ [num1 num2]
  (fn [f]
    (fn [x]
      (->> x
	   ((num1 f))
	   ((num2 f))))))

(defn dechurchify [church-number]
  ((church-number inc) 0))

;;(dechurchify (church+ church-one church-two)) => 3

