package models

case class UserLogin(username: String,
                     password: String
                      )

case class User(user_id: Int,
                username: String,
                email : String,
                password: String
                 )

object User
{
  //Temporary serves as user data source
  val users = List(
    User(1, "ricky", "ricky@abc.com", "password1"),
    User(2, "bobby", "bobby@abc.com", "password2"),
    User(3, "jacky", "jacky@abc.com", "password3"),
    User(4, "roger", "roger@abc.com", "password4"),
    User(5, "steve", "steve@abc.com", "password5")
  )

  def find_all = users
  def find_by_username_password(username: String, password: String): Option[User] = users.find((u: User) => u.username == username && u.password == password)
}