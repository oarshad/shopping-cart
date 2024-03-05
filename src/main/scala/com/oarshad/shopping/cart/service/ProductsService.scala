package com.oarshad.shopping.cart.service

import cats.effect.Sync
import cats.implicits.catsSyntaxApplicativeId
import com.oarshad.shopping.cart.domain.Product

trait ProductsService[F[_]] {
  def getProduct(id: String): F[Option[Product]]
  def getProducts: F[List[Product]]
}

object ProductsService {

  def apply[F[_] : Sync](): ProductsService[F] = new ProductsService[F] {

    private val products = List(
      Product("1", "Apple", 60),
      Product("2", "Orange", 25)
    )

    override def getProduct(id: String): F[Option[Product]] = products.find(_.id == id).pure[F]

    override def getProducts: F[List[Product]] = products.pure[F]
  }
}
