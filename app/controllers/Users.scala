package controllers

import play.api.mvc._
import play.api.data._

import models._

object Users extends Controller
{
  def list = Action { implicit request =>
    val users = User.find_all
    Ok(views.html.list(users))
  }

  val login_form: Form[UserLogin] = Form(
    Forms.mapping(
      "username" -> Forms.nonEmptyText,
      "password" -> Forms.nonEmptyText
    )(UserLogin.apply)(UserLogin.unapply)
  )

  def authenticate = Action { implicit request =>
    login_form.bindFromRequest.fold(
        form_with_errors => {
          Ok(views.html.login(form_with_errors))
        },
        form_data => {
          val valid_user = User.find_by_username_password(form_data.username, form_data.password)
          valid_user match {
            case Some(u) => Ok(s"Valid user: ${u.username}").withSession(Session(Map("user" -> u.username)))
            case _ => Ok("Incorrect username OR password")
          }
        }
    )
  }

  def login = Action { implicit request =>
    Ok(views.html.login(login_form))
  }
}