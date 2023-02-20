package net.martinprobson.example.layers

// Procedural, externally defined interface
class ConnectionPool(url: String):

  def close(): Unit = ()

  override def toString(): String = s"ConnectionPool $url"
end ConnectionPool
