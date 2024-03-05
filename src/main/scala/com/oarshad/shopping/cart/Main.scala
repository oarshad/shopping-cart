package com.oarshad.shopping.cart

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    IO.println("Hello, World!").as(ExitCode.Success)

}
