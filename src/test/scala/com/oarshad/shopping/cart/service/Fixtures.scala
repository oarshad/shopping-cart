package com.oarshad.shopping.cart.service

import com.oarshad.shopping.cart.domain.Product

trait Fixtures {

  val apple: Product = Product("1", "Apple", 60)
  val orange: Product = Product("2", "Orange", 25)

}
