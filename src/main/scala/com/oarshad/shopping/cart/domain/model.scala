package com.oarshad.shopping.cart.domain

case class Product(id: String, name: String, price: Int)

case class Promotion(id: String, buy: Int, forThePriceOf: Int)
