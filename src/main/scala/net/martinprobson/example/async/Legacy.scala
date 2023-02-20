package net.martinprobson.example.async

object Legacy extends App:
  def login(onSuccess: User => Unit, onFailure: AuthError => Unit): Unit =
    //onFailure(AuthError("Something has gone wrong!"))
    onSuccess(User(1, "Martin"))

  login(u => println(s"Got a: $u"), e => println(s"Error ${e.msg}"))

end Legacy
