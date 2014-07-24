package controllers

import play.api.mvc._
import play.api.data._

import models._

object Users extends Controller
{

  def list = Action {
    val users = User.get_all
    Ok(users.toString())
  }

  // Login module - STARTS

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
        val valid_user = User
          .get_by_username(form_data.username)
          .filter(_.check_password(form_data.password))

        valid_user match {
          case Some(valid_user) => {
            /*
            TODO: someway to store current user encrypted session in a cookie or somewhere
            */

            Ok(s"""Valid user:
                User ID:  ${valid_user.user_id}
                Username: ${valid_user.username}
                Email:    ${valid_user.email}""")
          }

          case _ => {
            Ok("Incorrect username OR password")
          }
        }
      }
    )
  }

  def login = Action { request =>
    Ok(views.html.login(login_form))
  }

  // Login module - ENDS

}