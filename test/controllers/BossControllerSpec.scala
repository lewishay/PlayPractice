package controllers

import common.Common
import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._
import services.BattleNetService

import scala.concurrent.{ExecutionContext, Future}

class BossControllerSpec extends ControllerBaseSpec {

  val uncalledService: BattleNetService = mock[BattleNetService]
  val calledService: BattleNetService = mock[BattleNetService]

  (calledService.getBoss(_: Int)(_: ExecutionContext))
    .expects(*, *)
    .returns(Future.successful(Common.exampleBoss))

  val ec: ExecutionContext = ExecutionContext.global

  "Calling the blankBoss action" should {

    val result: Future[Result] = new BossController(cc, uncalledService, ec).blankBoss(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the getBoss action" when {

    "there are no errors in the form" should {

      val result: Future[Result] = new BossController(cc, calledService, ec).getBoss(
        fakeRequest.withFormUrlEncodedBody("bossID" -> "77")
      )

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "there are errors in the form" should {

      val result: Future[Result] = new BossController(cc, uncalledService, ec).getBoss(
        fakeRequest.withFormUrlEncodedBody("bossID" -> "text")
      )

      "return 400" in {
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }
}
