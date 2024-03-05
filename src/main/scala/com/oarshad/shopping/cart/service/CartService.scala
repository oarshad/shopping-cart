package com.oarshad.shopping.cart.service

import cats.effect.Sync
import com.oarshad.shopping.cart.domain.Product

import scala.collection.mutable.ListBuffer

trait CartService[F[_]] {
  def addProduct(product: Product): F[Unit]
  def checkout(): F[Int]
  def cartItems: F[List[Product]]
}

object CartService {
  def apply[F[_] : Sync]: CartService[F] = new CartService[F] {

    private val cart = ListBuffer.empty[Product]

    override def addProduct(product: Product): F[Unit] = Sync[F].delay {
      cart.synchronized {
        cart += product
      }
    }

    override def checkout(): F[Int] = Sync[F].delay(cart.map(_.price).sum)

    override def cartItems: F[List[Product]] = Sync[F].delay(cart.toList)
  }
}
