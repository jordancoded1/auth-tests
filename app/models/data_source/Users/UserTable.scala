package models.data_source.Users

import scala.slick.driver.MySQLDriver.simple._

class UserTable(tag: Tag) extends Table[(Int, String, String, String, String, String, String, String, String, String)](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey)
  def created_at = column[String]("created_at")
  def updated_at = column[String]("updated_at")
  def username = column[String]("username")
  def email = column[String]("email")
  def encrypted_password = column[String]("encrypted_password")
  def confirmation_token = column[String]("confirmation_token")
  def remember_token = column[String]("remember_token")
  def first_name = column[String]("first_name")
  def last_name = column[String]("last_name")
  def * = (id, created_at, updated_at, username, email, encrypted_password, confirmation_token, remember_token, first_name, last_name)
}

object UserTable {
  val users = TableQuery[UserTable]
}