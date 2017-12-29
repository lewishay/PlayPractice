package controllers.auth

import java.util.Base64

import controllers.ControllerBaseSpec

class AuthenticationHelpersSpec extends ControllerBaseSpec {

  "Parsing the auth header" when {

    "a valid auth header with a username and password is given" should {

      "return a credentials object" in {
        val encodedString: String = Base64.getEncoder.encodeToString("Pete:Zahut".getBytes)
        val authHeader: String = s"Basic $encodedString"
        val expectedCreds: Option[Credentials] = Some(Credentials("Pete", "Zahut"))
        val result: Option[Credentials] = AuthenticationHelpers.parseAuthHeader(authHeader)
        result shouldBe expectedCreds
      }
    }

    "a valid auth header without a clear username and password is given" should {

      "return None" in {
        val encodedString: String = Base64.getEncoder.encodeToString("Pizza5000".getBytes)
        val authHeader: String = s"Basic $encodedString"
        val result: Option[Credentials] = AuthenticationHelpers.parseAuthHeader(authHeader)
        result shouldBe None
      }
    }

    "an invalid auth header is given" should {

      "return None" in {
        val result: Option[Credentials] = AuthenticationHelpers.parseAuthHeader("blarg")
        result shouldBe None
      }
    }
  }
}