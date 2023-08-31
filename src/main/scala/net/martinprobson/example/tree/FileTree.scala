package net.martinprobson.example.tree

import net.martinprobson.example.ZIOApplication
import zio.*

sealed trait File {
  def show: String = this.toString()
  def setParent(parent: Directory): File
}

case class RegularFile(name: String, parent: Directory, size: Int) extends File {
  def setParent(parent: Directory): File = {
    RegularFile(this.name, parent, this.size)
  }
}

object RegularFile {
  def apply(name: String, size: Int): RegularFile = RegularFile(name, null, size)
}

case class Directory(name: String, parent: Directory, files: List[File]) extends File {
  def setParent(parent: Directory): File = {
    Directory(this.name, parent, this.files)
  }

  def add(f: File): Directory = {
    val t = f.setParent(this)
    Directory(this.name, this.parent, t :: files)
  }
}

object Directory {
  def apply(name: String, files: List[File]): Directory = Directory(name, null, files)
  def empty(name: String): Directory = Directory(name, null, List())
}

object FTree extends App {

  val d = Directory.empty("/").add(RegularFile("a.txt", 10))
//  val d2: Directory = Directory("dir2", d, List())
//    .add(RegularFile("c", 100))
//    .add(RegularFile("d", 1000))
//  val t = d
//    .add(RegularFile("a", 10))
//    .add(RegularFile("b", 11))
//    .add(d2)
  println(d.show)

}
