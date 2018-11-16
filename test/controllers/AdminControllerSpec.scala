package controllers

import common.SessionKeys
import forms.LoginForm
import org.scalamock.scalatest.MockFactory
import play.api.http.Status
import play.api.test.Helpers._

class AdminControllerSpec extends ControllerBaseSpec with MockFactory {

  val loginForm = new LoginForm(mockAppConfig)
  val controller = new AdminController(cc, loginForm, mockAppConfig)

  "Calling the admin action" when {

    "user is logged in as an admin" should {
      val adminRequest = fakeRequest.withSession(SessionKeys.adminStatus -> "true")
      val result = controller.admin(adminRequest)

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "user is not logged in" should {
      val result = controller.admin(fakeRequest)

      "return 303" in {
        status(result) shouldBe Status.SEE_OTHER
      }

      "redirect the user to the login page" in {
        redirectLocation(result) shouldBe Some("/login")
      }
    }
  }

  "Calling the loginShow action" should {
    val result = controller.loginShow(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the login action" when {

    "the user has entered valid credentials" should {
      val result = controller.login(fakeRequest.withFormUrlEncodedBody("username" -> "admin", "password" -> "testPass"))

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "the user has entered an invalid username" should {
      val result = controller.login(fakeRequest.withFormUrlEncodedBody("username" -> "hey", "password" -> "testPass"))

      "return 400" in {
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "the user has entered an invalid password" should {
      val result = controller.login(fakeRequest.withFormUrlEncodedBody("username" -> "admin", "password" -> "water"))

      "return 400" in {
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }

  "Calling the submitFeatures action" should {

    val result = controller.submitFeatures(fakeRequest)

    "return 303" in {
      status(result) shouldBe Status.SEE_OTHER
    }

    "redirect the user to the feature switch page" in {
      redirectLocation(result) shouldBe Some(routes.AdminController.admin().url)
    }
  }
}
