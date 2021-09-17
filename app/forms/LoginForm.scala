package forms

import javax.inject.{Inject, Singleton}
import config.AppConfig
import io.github.nremond.SecureHash
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid}

case class LoginForm(username: String, password: String)

@Singleton
class LoginFormImpl @Inject()(appConfig: AppConfig) {

  val usernameConstraint: Constraint[String] = Constraint {
    case user if user == "admin" => Valid
    case _ => Invalid("Invalid username")
  }

  val passwordConstraint: Constraint[String] = Constraint {
    case pass if pass.nonEmpty && SecureHash.createHash(pass, saltLength = 0) == appConfig.adminPasswordHash => Valid
    case _ => Invalid("Invalid password")
  }

  val form: Form[LoginForm] = Form(
    mapping(
      "username" -> text.verifying(usernameConstraint),
      "password" -> text.verifying(passwordConstraint)
    )(LoginForm.apply)(LoginForm.unapply)
  )
}
