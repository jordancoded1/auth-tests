package models

import models.data_source.Users._

case class UserLogin(username: String,
                     password: String)

case class UserSignup(username: String,
                      email: String,
                      password: String,
                      confirm_password: String)

case class User(id: Int,
                created_at: String,
                updated_at: String,
                username: String,
                email: String,
                encrypted_password: String,
                confirmation_token: String,
                remember_token: String,
                first_name: String,
                last_name: String)

object User extends UserData {
  def find_by_username_password(username: String, password: String): Option[User] = None /*users.find((u: User) => u.username == username && u.password == password)*/
}