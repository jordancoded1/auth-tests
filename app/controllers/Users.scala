package controllers

import play.api.mvc._
import play.api.data._

import models._

object Users extends Controller
{

  def list = Action { implicit request =>
    val users = User.get_all
    //Ok(views.html.list(users))
    Ok("List users!")
  }

  def login = Action { implicit request =>

    val login_form: Form[UserLogin] = Form(
      Forms.mapping(
        "username" -> Forms.nonEmptyText,
        "password" -> Forms.nonEmptyText
      )(UserLogin.apply)(UserLogin.unapply)
    )

    request.method match
    {
      case "GET" => Ok(views.html.login(login_form))

      case "POST" => {
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
    }
  }

  def logout = Action { implicit request =>
    Ok("TODO: Logout user")
  }

  def signup = Action { implicit request =>

    val signup_form: Form[UserSignup] = Form(
      Forms.mapping(
        "username"    -> Forms.nonEmptyText(4),
        "email"       -> Forms.email,
        "password"    -> Forms.nonEmptyText(8),
        "confirm_password" -> Forms.nonEmptyText(8)
      )(UserSignup.apply)(UserSignup.unapply)
      verifying("Passwords must match", f => f.password == f.confirm_password)
    )

    request.method match
    {
      case "GET" => Ok(views.html.signup(signup_form))

      case "POST" => {
        signup_form.bindFromRequest.fold(
          form_with_errors => {
            Ok(views.html.signup(form_with_errors))
          },
          form_data => {
            Ok("Create user!")
          }
        )
      }
    }
  }

}