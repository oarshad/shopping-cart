package com.oarshad.shopping.cart

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.oarshad.shopping.cart.domain.Promotion
import com.oarshad.shopping.cart.service.{CartService, ProductsService}

object Main extends IOApp {

  private val productsService = ProductsService[IO]()
  private val cartService = CartService[IO](
    List(
      Promotion("1", buy = 2, forThePriceOf = 1),
      Promotion("2", buy = 3, forThePriceOf = 2)
    )
  )

  private def printProducts: IO[Unit] =
    productsService.getProducts.map(_.foreach(p => println(s"${p.id} - ${p.name}")))

  private def input: IO[Unit] =
    printProducts >>
      IO.println("0 - Checkout") >>
      IO.println("X - Exit") >>
      IO.println("Make Selection ....") >>
      IO.readLine.flatMap {
        case "X" | "x" => IO.println("Exiting...")
        case "0"       => cartService.checkout() >>= (total => IO.println(s"Total Checkout: ${total / 100.00}"))
        case i =>
          (productsService.getProduct(i) >>= {
            case Some(p) =>
              cartService.addProduct(p) >> IO.println(s"Product Added: ${p.name}")
            case _ => IO.println(s"Invalid Product Id: $i")
          }) >> input
      }

  override def run(args: List[String]): IO[ExitCode] =
    input.as(ExitCode.Success)

}
