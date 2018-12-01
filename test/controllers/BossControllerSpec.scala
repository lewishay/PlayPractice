package controllers

import common.Common
import models.{Boss, ErrorModel}
import play.api.http.Status
import play.api.test.Helpers._
import services.BattleNetService

import scala.concurrent.{ExecutionContext, Future}

class BossControllerSpec extends ControllerBaseSpec {

  private trait Test {
    val service: BattleNetService = mock[BattleNetService]
    val serviceCall: Boolean = true
    val serviceReturn: Either[ErrorModel, Boss] = Right(Common.exampleBoss)

    def setup(serviceIsCalled: Boolean, futureReturn: Either[ErrorModel, Boss]): Any = {
      if(serviceIsCalled) {
        (service.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(futureReturn))
      }
    }

    def controller: BossController = {
      setup(serviceCall, serviceReturn)
      new BossController(service)
    }
  }

  "Calling the blankBoss action" should {

    "return 200" in new Test {
      override val serviceCall = false
      val result = controller.blankBoss(fakeRequest)
      status(result) shouldBe Status.OK
    }

    "return HTML" in new Test {
      override val serviceCall = false
      val result = controller.blankBoss(fakeRequest)
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the getBoss action with no errors in the form" when {

    "the service returns a Boss" should {

      "return 200" in new Test {
        val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "77"))
        status(result) shouldBe Status.OK
      }

      "return HTML" in new Test {
        val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "77"))
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "the service returns a response status of 404" should {

      "return 404" in new Test {
        override val serviceReturn: Either[ErrorModel, Boss] = Left(ErrorModel(Status.NOT_FOUND, "Can't find it :("))
        val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "77"))
        status(result) shouldBe Status.NOT_FOUND
      }

      "return HTML" in new Test {
        override val serviceReturn: Either[ErrorModel, Boss] = Left(ErrorModel(Status.NOT_FOUND, "Can't find it :("))
        val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "77"))
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "the service returns an unexpected response status" should {

      "return 500" in new Test {
        override val serviceReturn: Either[ErrorModel, Boss] = Left(ErrorModel(Status.GATEWAY_TIMEOUT, "Erm..."))
        val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "77"))
        status(result) shouldBe Status.INTERNAL_SERVER_ERROR
      }

      "return HTML" in new Test {
        override val serviceReturn: Either[ErrorModel, Boss] = Left(ErrorModel(Status.GATEWAY_TIMEOUT, "Erm..."))
        val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "77"))
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }

  "Calling the getBoss action when there are errors in the form" should {

    "return 400" in new Test {
      override val serviceCall: Boolean = false
      val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "text"))
      status(result) shouldBe Status.BAD_REQUEST
    }

    "return HTML" in new Test {
      override val serviceCall: Boolean = false
      val result = controller.getBoss(fakeRequest.withFormUrlEncodedBody("bossID" -> "text"))
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }
}
