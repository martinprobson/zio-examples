package net.martinprobson.example.tree

import net.martinprobson.example.ZIOApplication
import zio.*

object NonBinaryTree extends ZIOApplication {

  case class Node[A](value: A, size: Int, children: List[Node[A]], var sum: Int = 0) {
    override def toString: String = {
      def format(level: Int, node: Node[A]): String =
        if (node != null) {
          val indent = if (level == 0) "" else " ".repeat(level) + "|" + "-".repeat(level)
          s"${indent}${node.value} --- ${node.sum}\n" +
            node.children.foldLeft("") { (s, n) => s + format(level + 1, n) }
        } else {
          ""
        }
      format(0, this)
    }
  }

  val tree = Node(
    "/",
    0,
    List(
      Node(
        "home",
        0,
        List(
          Node(
            "martinr",
            0,
            List(
              Node("vimwiki", 0, List(Node("a", 100, List()), Node("b", 200, List()))),
              Node("Projects", 0, List(Node("git", 0, List(Node("fred,txt", 300, List()))))),
              Node("temp.txt", 400, List())
            )
          )
        )
      ),
      Node("swapfile", 1000, List()),
      Node("kernel", 10, List())
    )
  )

  def sum[A](n: Node[A]): UIO[Int] = {
    if (n == null)
      ZIO.logInfo("In sum") *> ZIO.succeed(0)
    else {
      for {
        _ <- ZIO.logInfo("In sum")
        z <- ZIO.foreachPar(n.children) { sum(_) }
        z1 <- ZIO.succeed(z.sum + n.size)
        _ <- ZIO.succeed(n.sum = z1)
      } yield (z1)
    }
  }

  def run = for {
    res <- sum(tree)
    _ <- ZIO.logInfo(s"Res = $res")
    _ <- Console.printLine(tree)
    _ <- ZIO.logInfo("Done")
  } yield ()
}
