package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._
import services.BattleNetService

import scala.concurrent.{ExecutionContext, Future}

class BossControllerSpec extends ControllerBaseSpec {

  val mockService: BattleNetService = mock[BattleNetService]
  val ec: ExecutionContext = mock[ExecutionContext]

  "Calling the blankBoss action" should {

    val result: Future[Result] = new BossController(cc, mockService, ec).blankBoss(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  //TODO
}
