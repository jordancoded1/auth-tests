package controllers

import play.api.mvc._
import play.api.data._

import models._

object Users extends Controller
{
  val signup_form: Form[UserSignup] = Form(
    Forms.mapping(
      "username"   -> Forms.nonEmptyText(3).verifying("Username has been taken", User.get_by_username(_).isEmpty),
      "email"      -> Forms.email.verifying("Email address has been taken", User.get_by_email(_).isEmpty),
      "password"   -> Forms.nonEmptyText(8),
      "confirm_password"   -> Forms.text
    )(UserSignup.apply)(UserSignup.unapply) verifying("Passwords must match", f => f.password == f.confirm_password)
  )

  val login_form: Form[UserLogin] = Form(
    Forms.mapping(
      "username" -> Forms.nonEmptyText,
      "password" -> Forms.nonEmptyText
    )(UserLogin.apply)(UserLogin.unapply)
  )

  def signup = Action { implicit request =>
    Ok(views.html.signup(signup_form))
  }

  def login = Action { implicit request =>
    Ok(views.html.login(login_form))
  }

  def logout = Action { implicit request =>
    Ok("Logged out").withNewSession
  }

  def signupProcess = Action { implicit request =>
    signup_form.bindFromRequest.fold(
      form_with_errors => Ok(views.html.signup(form_with_errors)),
      form_data => {
        User.signup(form_data.username, form_data.email, form_data.password) match {
          case Some(1) => Ok("Successfully signed up!")
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
          case Some(u) => Ok(s"Valid user: ${u.username.getOrElse("")}").withSession(Session(Map("user" -> {u.username.getOrElse("")} )))
          case _ => Ok("Incorrect username OR password")
        }
      }
    )
  }

  def list = Action { implicit request =>
    Ok(views.html.list(User.get_all))
  }
}