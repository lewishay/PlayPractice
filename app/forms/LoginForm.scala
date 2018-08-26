package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class LoginForm(username: String, password: String)

object LoginForm {

  val loginForm = Form(
    mapping(
      "username" -> text.verifying("Username not recognised", _ == "admin"),
      "password" -> of[String].verifying("Invalid password", _ == "cactus")
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
