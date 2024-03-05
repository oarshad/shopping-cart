package com.oarshad.shopping.cart.service

import cats.effect.IO
import munit.CatsEffectSuite

class CartServiceSpec extends CatsEffectSuite with Fixtures {

  test("should return empty cart when no product added") {
    val cartService = CartService[IO]
    val result = cartService.cartItems

    assertIO(result, List.empty)
  }

  test("should add product to cart") {
    val cartService = CartService[IO]
    val result = for {
      _ <- cartService.addProduct(apple)
      items <- cartService.cartItems
    } yield items

    assertIO(result, List(apple))
  }

  test("should add multiple products to cart") {
    val cartService = CartService[IO]
    val result = for {
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(orange)
      items <- cartService.cartItems
    } yield items

    assertIO(result, List(apple, orange))
  }

  test("should return correct total checkout") {
    val cartService = CartService[IO]
    val result = for {
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(orange)
      total <- cartService.checkout()
    } yield total

    assertIO(result, 205)
  }

}
