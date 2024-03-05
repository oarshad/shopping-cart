# Scala Shopping Cart Exercise

Build and run the application

```shell
sbt clean compile run
```

Run the tests

```shell
sbt test
```

## Main Code
- [ShoppingCart.scala](src/main/scala/com/oarshad/shopping/cart/service/CartService.scala)
- [ShoppingCartSpec.scala](src/test/scala/com/oarshad/shopping/cart/service/CartServiceSpec.scala)


## Run Locally - Command Line
```shell
sbt clean compile run
```
This should run the application and print the output on the console,
giving user the ability to make a selection as below:
```shell
1 - Apple
2 - Orange
0 - Checkout
X - Exit
Make Selection ....
```
Select the items and press 0 to checkout or X to exit.
