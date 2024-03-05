package com.oarshad.shopping.cart.service

import cats.effect.Sync
import com.oarshad.shopping.cart.domain.{Product, Promotion}

import scala.collection.mutable.ListBuffer

trait CartService[F[_]] {
  def addProduct(product: Product): F[Unit]
  def checkout(): F[Int]
  def cartItems: F[List[Product]]
}

object CartService {
  def apply[F[_] : Sync](promotions: List[Promotion] = List.empty): CartService[F] = new CartService[F] {

    private val cart = ListBuffer.empty[Product]

    override def addProduct(product: Product): F[Unit] = Sync[F].delay {
      cart.synchronized {
        cart += product
      }
    }

    override def checkout(): F[Int] = Sync[F].delay {
      cart
        .groupBy(_.id)
        .map {
          case (id, products) =>
            val promotion = promotions.find(_.id == id)
            promotion match {
              case Some(promo) =>
                val qty = (products.length % promo.buy) + (products.length / promo.buy * promo.forThePriceOf)
                products.head.price * qty
              case _ => products.length * products.head.price
            }
        }
        .sum
    }

    override def cartItems: F[List[Product]] = Sync[F].delay(cart.toList)
  }
}
