package models.data_source.Users

import scala.slick.driver.MySQLDriver.simple._
import models.User

class UserTable(tag: Tag) extends Table[(Int, Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String])](tag, "users") {
  def id                = column[Int]("id", O.PrimaryKey)
  def createdAt         = column[Option[String]]("created_at")
  def updatedAt         = column[Option[String]]("updated_at")
  def username          = column[Option[String]]("username")
  def email             = column[Option[String]]("email")
  def encryptedPassword = column[Option[String]]("encrypted_password")
  def confirmationToken = column[Option[String]]("confirmation_token")
  def rememberToken     = column[Option[String]]("remember_token")
  def firstName         = column[Option[String]]("first_name")
  def lastName          = column[Option[String]]("last_name")

  def * = (id, createdAt, updatedAt, username, email, encryptedPassword, confirmationToken, rememberToken, firstName, lastName)
}

object UserTable {
  val users = TableQuery[UserTable]
}