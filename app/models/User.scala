package models

case class UserLogin(username: String,
                     password: String
                      )

case class User(user_id: Int,
                username: String,
                email : String,
                password: String
                 ) {
  def check_password(password: String): Boolean = this.password == password
}

object User
{
  //TEMP DATA SOURCE
  val users = List(
    User(1, "ricky", "ricky@abc.com", "password1"),
    User(2, "bobby", "bobby@abc.com", "password2"),
    User(3, "jacky", "jacky@abc.com", "password3"),
    User(4, "roger", "roger@abc.com", "password4"),
    User(5, "steve", "steve@abc.com", "password5")
  )

  def get_all = users

  def get_by_username(username: String): Option[User] = {
    users.filter(_.username == username).headOption
  }
}

