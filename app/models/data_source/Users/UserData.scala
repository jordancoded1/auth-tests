package models.data_source.Users

import models.User
import play.api.Logger
import play.api.Play._
import scala.slick.driver.MySQLDriver.simple._
import play.api.db.DB

trait UserData {

  val users = UserTable.users
  val db = Database.forDataSource(DB.getDataSource())

  def get_all = db.withSession { implicit session =>
    Logger.info(users.selectStatement)
    users.list
  }
}