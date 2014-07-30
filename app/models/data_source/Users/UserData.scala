package models.data_source.Users

import play.api.Logger
import play.api.Play._
import scala.slick.driver.MySQLDriver.simple._
import play.api.db.DB
import models.UserMain

trait UserData {
  val users = UserTable.users
  val db = Database.forDataSource(DB.getDataSource())

  def insert(user: UserMain): Option[Int] = db.withSession { implicit session =>
    Some(users += user)
  }

  def get_all: List[UserMain] = db.withSession { implicit session =>
    Logger.info("[QUERY]: " + users.selectStatement)
    users.list
  }

  def get_by_username(username: String): Option[UserMain] = db.withSession { implicit session =>
    val q = users.filter(_.username === username)
    Logger.info("[QUERY]: " + q.selectStatement)
    q.list.headOption
  }

  def get_by_email(email: String): Option[UserMain] = db.withSession { implicit session =>
    val q = users.filter(_.email === email)
    Logger.info("[QUERY]: " + q.selectStatement)
    q.list.headOption
  }
}