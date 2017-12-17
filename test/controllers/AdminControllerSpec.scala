package controllers

import controllers.auth.{AuthenticationHelpers, Credentials}
import play.api.http.Status
import play.api.mvc.Headers
import play.api.test.FakeRequest
import play.api.test.Helpers._

class AdminControllerSpec extends ControllerBaseSpec {

  "Calling the admin action" when {

    "user is logged in as an admin" should {

      val adminRequest = FakeRequest().withHeaders(
        Headers(("Authorization", AuthenticationHelpers.authHeaderValue(Credentials("admin", "cactus"))))
      )
      val result = new AdminController(cc).admin(adminRequest)

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "user is not logged in" should {

      val result = new AdminController(cc).admin(FakeRequest())

      "return 401" in {
        status(result) shouldBe Status.UNAUTHORIZED
      }
    }
  }
}
