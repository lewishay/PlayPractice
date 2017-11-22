/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers.auth

import java.util.Base64

import AuthenticationHelpers._
import akka.util.ByteString
import play.api.libs.streams.Accumulator
import play.api.mvc._
import play.api.mvc.Results.Unauthorized
import play.api.mvc.Security.AuthenticatedBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object AuthenticatedAction extends AuthenticatedBuilder(
  userinfo = _.headers.get("Authorization")
    .flatMap(parseAuthHeader)
    .flatMap(validateUser),
  defaultParser = bodyParser,
  onUnauthorized = { _ =>
    Unauthorized(views.html.unauthorised())
      .withHeaders("WWW-Authenticate" -> """Basic realm="Login"""")
  }
)

object AuthenticationHelpers {
  val validCredentials = Set(
    Credentials("admin", "cactus")
  )

  def authHeaderValue(credentials: Credentials): String =
    "Basic " + Base64.getEncoder.encodeToString(s"${credentials.username}:${credentials.password}".getBytes)

  def parseAuthHeader(authHeader: String): Option[Credentials] =
    authHeader.split("""\s""") match {
      case Array("Basic", userAndPass) =>
        new String(Base64.getDecoder.decode(userAndPass), "UTF-8").split(":") match {
          case Array(user, password) => Some(Credentials(user, password))
          case _                     => None
        }
      case _ => None
    }

  def validateUser(c: Credentials): Option[String] =
    if (validCredentials.contains(c))
      Some(c.username)
    else
      None

  val bodyParser: BodyParser[AnyContent] = new BodyParser[AnyContent] {
    override def apply(v1: RequestHeader): Accumulator[ByteString, Either[Result, AnyContent]] = ???
  }
}

case class Credentials(username: String, password: String)
