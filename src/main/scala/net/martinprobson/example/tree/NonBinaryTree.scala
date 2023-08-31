package net.martinprobson.example.tree

import net.martinprobson.example.ZIOApplication
import zio.*

object NonBinaryTree extends ZIOApplication {

  case class Node[A](value: A, children: List[Node[A]], var sum: Int = 0) {
    override def toString: String = {
      def format(level: Int, node: Node[A]): String =
        if (node != null) {
          val indent = if (level == 0) "" else " ".repeat(level) + s"$level" + "-".repeat(level)
          s"${indent}${node.value}\n" +
            node.children.foldLeft("") { (s, n) => s + format(level + 1, n) }
        } else {
          ""
        }
      format(0, this)
    }
  }

  val tree = Node(
    "/",
    List(
      Node(
        "/home",
        List(
          Node(
            "/home/martinr",
            List(
              Node(
                "/home/martinr/vimwiki",
                List(
                  Node("/home/martinr/vimwiki/a", List()),
                  Node("/home/martinr/vimwiki/b", List())
                )
              ),
              Node("/home/martinr/Projects", List(Node("/home/martinr/Projects/git", List()))),
              Node("/home/martinr/temp.txt", List())
            )
          )
        )
      ),
      Node("/swapfile", List())
    )
  )

//  def preOrder(n: Node[Int]): UIO[Int] = {
//    if (n == null)
//      ZIO.succeed(0)
//    else {
//      for {
//        _ <- ZIO.logDebug(s"Node ${n.value}")
//        v <-
//          if (n.left == null && n.right == null)
//            ZIO.succeed(n.value)
//          else
//            ZIO.succeed(0)
//        z <- preOrder(n.left).zipPar(preOrder(n.right))
//        z1 <- ZIO.succeed(z._1 + z._2 + v)
//        _ <- ZIO.succeed(n.sum = z1)
//        _ <- ZIO.logInfo(s"Node: $n z1 = $z1")
//      } yield (z1)
//    }
//  }
//
  def run = for {
    _ <- Console.printLine(tree)
    _ <- ZIO.logInfo("Done")
  } yield ()
}
