package models

import models.data_source.Users._

case class UserLogin(username: String,
                     password: String)

case class UserSignup(username: String,
                      email: String,
                      password: String,
                      confirm_password: String)

case class User(id: Int,
                createdAt: Option[String],
                updatedAt: Option[String],
                username: Option[String],
                email: Option[String],
                encryptedPassword: Option[String],
                confirmationToken: Option[String],
                rememberToken: Option[String],
                firstName: Option[String],
                lastName: Option[String])


object User extends UserData {
  def find_by_username_password(username: String, password: String): Option[User] = None /*users.find((u: User) => u.username == username && u.password == password)*/
}