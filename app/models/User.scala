package models

import models.data_source.Users._
import org.mindrot.jbcrypt.BCrypt
import scala.compat.Platform
import java.sql.Timestamp

case class UserSignup(username: String,
                      email: String,
                      password: String,
                      confirm_password: String)

case class UserLogin(username: String,
                     password: String)

case class UserMain(id: Option[Int],
                createdAt: Option[String],
                updatedAt: Option[String],
                username: Option[String],
                email: Option[String],
                encryptedPassword: Option[String],
                confirmationToken: Option[String],
                rememberToken: Option[String],
                firstName: Option[String],
                lastName: Option[String])

object User extends UserData
{
  def signup(username: String, email: String, password: String): Option[Int] =
  {
    val timestamp = new Timestamp(Platform.currentTime)

    this.insert(
      UserMain(Some(0),
        Some(timestamp.toString()),
        Some(timestamp.toString()),
        Some(username),
        Some(email),
        Some(BCrypt.hashpw(password, BCrypt.gensalt(12))),
        Some(""),
        Some(""),
        Some(""),
        Some(""))
    )
  }

  def authenticate(username: String, password: String): Option[UserMain] =
  {
    val user = this.get_by_username(username)
    user match {
      case Some(u) => if ( BCrypt.checkpw(password, u.encryptedPassword.getOrElse("")) ) user else None
      case _ => None
    }
  }
}