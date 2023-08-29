package net.martinprobson.example.recursion

object Recursion extends App {

  def factorial(n: BigInt): BigInt = if (n == 0) 1 else n * factorial(n - 1)

  println(factorial(BigInt(100000)))
  //println(factorial(BigInt(10)))

}
