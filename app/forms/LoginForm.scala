package forms

import play.api.data.Form
import play.api.data.Forms._

case class LoginForm(username: String, password: String)

object LoginForm {

  val loginForm = Form(
    mapping(
      "username" -> text.verifying("Username not recognised", _ == "admin"),
      "password" -> text.verifying("Invalid password", _ == "cactus")
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
