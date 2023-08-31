package net.martinprobson.example.tree

import net.martinprobson.example.ZIOApplication
import zio.*

object Tree extends ZIOApplication {
  case class Node[A](value: A, left: Node[A], right: Node[A], var sum: Int = 0) {
    def printTree: Unit = {
      def printTree(level: Int, node: Node[A]): Unit =
        if (node != null) {
          val indent = if (level == 0) "" else " ".repeat(level) + s"$level" + "-".repeat(level)
          println(s"$indent Node: ${node.value}")
          printTree(level + 1, node.left)
          printTree(level + 1, node.right)
        }
      printTree(0, this)
    }
    def format: String = {
      def format(level: Int, node: Node[A]): String =
        if (node != null) {
          val indent = if (level == 0) "" else " ".repeat(level) + s"$level" + "-".repeat(level)
          s"$indent Node: ${node.value}\n" +
            format(level + 1, node.left) +
            format(level + 1, node.right)
        } else {
          ""
        }
      format(0, this)
    }
  }

  val tree = Node(
    1,
    Node(2, Node(4, null, null), Node(5, null, null)),
    Node(3, Node(6, Node(7, null, null), Node(8, null, Node(9, null, null))), null)
  )

  def preOrder(n: Node[Int]): UIO[Int] = {
    if (n == null)
      ZIO.succeed(0)
    else {
      for {
        _ <- ZIO.logDebug(s"Node ${n.value}")
        v <-
          if (n.left == null && n.right == null)
            ZIO.succeed(n.value)
          else
            ZIO.succeed(0)
        z <- preOrder(n.left).zipPar(preOrder(n.right))
        z1 <- ZIO.succeed(z._1 + z._2 + v)
        _ <- ZIO.succeed(n.sum = z1)
        _ <- ZIO.logInfo(s"Node: $n z1 = $z1")
      } yield (z1)
    }
  }

  def run = for {
    res <- preOrder(tree)
    _ <- ZIO.logInfo(s"Res = $res")
    _ <- ZIO.logInfo(s"Tree = $tree")
    s <- ZIO.succeed(tree.format)
    _ <- Console.printLine(s)
    _ <- ZIO.logInfo("Done")
  } yield ()
}
