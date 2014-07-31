package controllers

import play.api.mvc._
import play.api.data._

import models._

object Users extends Controller
{

  implicit def sessioned_user(implicit request: RequestHeader): Option[String] = {
    request.session.get("user")
  }

  val signup_form: Form[UserSignup] = Form(
    Forms.mapping(
      "username"   -> Forms.nonEmptyText(3).verifying("Username has been taken", User.get_by_username(_).isEmpty),
      "email"      -> Forms.email.verifying("Email address has been taken", User.get_by_email(_).isEmpty),
      "password"   -> Forms.nonEmptyText(8),
      "confirm_password"   -> Forms.text
    )(UserSignup.apply)(UserSignup.unapply)verifying("Passwords must match", f => f.password == f.confirm_password)
  )

  val login_form: Form[UserLogin] = Form(
    Forms.mapping(
      "username" -> Forms.nonEmptyText,
      "password" -> Forms.nonEmptyText
    )(UserLogin.apply)(UserLogin.unapply)
  )

  def signup = Action { implicit request =>
    sessioned_user match {
      case Some(u) => Redirect(routes.Users.list())
      case _ => Ok(views.html.signup(signup_form))
    }
  }

  def login = Action { implicit request =>
    sessioned_user match {
      case Some(u) => Redirect(routes.Users.list())
      case _ => Ok(views.html.login(login_form))
    }
  }

  def logout = Action { implicit request =>
    Redirect(routes.Users.login()).withNewSession
  }

  def signupProcess = Action { implicit request =>
    signup_form.bindFromRequest.fold(
      form_with_errors => Ok(views.html.signup(form_with_errors)),
      form_data => {
        User.signup(form_data.username, form_data.email, form_data.password) match {
          case Some(1) => Redirect(routes.Users.login()).flashing(("message", s"Great! You can now login with the username - ${form_data.username}"))
          case _ => Ok(views.html.signup(signup_form))
        }
      }
    )
  }

  def loginProcess = Action { implicit request =>
    login_form.bindFromRequest.fold(
      form_with_errors => Ok(views.html.login(form_with_errors)),
      form_data => {
        User.authenticate(form_data.username, form_data.password) match {
          case Some(u) => Redirect(routes.Users.list()).withSession(Session(Map("user" -> {u.username.getOrElse("")} )))
          case _ => Redirect(routes.Users.login()).flashing(("message", "Invalid username AND/OR password"))
        }
      }
    )
  }

  def list = Action { implicit request =>
    Ok(views.html.list(User.get_all))
  }

}