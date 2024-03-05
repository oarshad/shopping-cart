package com.oarshad.shopping.cart.service

import cats.effect.IO
import com.oarshad.shopping.cart.domain.Promotion
import munit.CatsEffectSuite

class CartServiceSpec extends CatsEffectSuite with Fixtures {

  test("should return empty cart when no product added") {
    val cartService = CartService[IO]()
    val result = cartService.cartItems

    assertIO(result, List.empty)
  }

  test("should add product to cart") {
    val cartService = CartService[IO]()
    val result = for {
      _ <- cartService.addProduct(apple)
      items <- cartService.cartItems
    } yield items

    assertIO(result, List(apple))
  }

  test("should add multiple products to cart") {
    val cartService = CartService[IO]()
    val result = for {
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(orange)
      items <- cartService.cartItems
    } yield items

    assertIO(result, List(apple, orange))
  }

  test("should return correct total checkout") {
    val cartService = CartService[IO]()
    val result = for {
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(orange)
      total <- cartService.checkout()
    } yield total

    assertIO(result, 205)
  }

  test("should do correct checkout with buy 2 for 1 promotion") {
    val cartService = CartService[IO](List(
      Promotion("1", buy = 2, forThePriceOf = 1)
    ))
    val result = for {
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      total <- cartService.checkout()
    } yield total

    assertIO(result, 60)
  }

  test("should do correct checkout with buy 3 for 2 promotion") {
    val cartService = CartService[IO](List(
      Promotion("2", buy = 3, forThePriceOf = 2)
    ))
    val result = for {
      _ <- cartService.addProduct(orange)
      _ <- cartService.addProduct(orange)
      _ <- cartService.addProduct(orange)
      total <- cartService.checkout()
    } yield total

    assertIO(result, 50)
  }

  test("should do correct checkout with multiple items with promotions") {
    val cartService = CartService[IO](List(
      Promotion("1", buy = 2, forThePriceOf = 1),
      Promotion("2", buy = 3, forThePriceOf = 2)
    ))
    val result = for {
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(apple)
      _ <- cartService.addProduct(orange)
      _ <- cartService.addProduct(orange)
      _ <- cartService.addProduct(orange)
      _ <- cartService.addProduct(orange)
      total <- cartService.checkout()
    } yield total

    assertIO(result, 195)
  }

}
