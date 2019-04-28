package forms

import javax.inject.{Inject, Singleton}

import config.AppConfig
import io.github.nremond.SecureHash
import play.api.data.Form
import play.api.data.Forms._

case class LoginForm(username: String, password: String)

@Singleton
class LoginFormImpl @Inject()(appConfig: AppConfig) {

  val passwordCheck: String => Boolean =
    SecureHash.createHash(_, saltLength = 0) == appConfig.adminPasswordHash

  val form = Form(
    mapping(
      "username" -> text.verifying("Username not recognised", _ == "admin"),
      "password" -> text.verifying("Invalid password", passwordCheck)
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
