package models.data_source.Users

import models.User
import play.api.Logger
import play.api.Play._
import scala.slick.jdbc.JdbcBackend._
import play.api.db.DB

trait UserData {

  val users = UserTable.users
  val db = Database.forDataSource(DB.getDataSource("default"))

  def get_all: Option[List[User]] = db.withSession { session =>
    Logger.debug("Fetching all users...")
    // TODO: Fetch all users here
    
    None
  }
}