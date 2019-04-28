package helpers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest._
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

trait IntegrationBaseSpec extends WordSpecLike with Matchers with OptionValues with WireMockHelper
  with GuiceOneServerPerSuite with BeforeAndAfterEach with BeforeAndAfterAll {

  val mockUrl: String = WireMockHelper.wireMockUrl

  lazy val injector: Injector = app.injector
  lazy val client: WSClient = injector.instanceOf[WSClient]

  def servicesConfig: Map[String, String] = Map(
    "services.protocol" -> "http",
    "services.battle-net.url" -> mockUrl
  )

  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .configure(servicesConfig)
    .build()

  override def beforeAll(): Unit = {
    super.beforeAll()
    startWireMock()
  }

  override def afterAll(): Unit = {
    stopWireMock()
    super.afterAll()
  }

  def buildRequest(path: String): WSRequest = client.url(s"http://localhost:$port/$path").withFollowRedirects(false)

  def document(response: WSResponse): Document = Jsoup.parse(response.body)

  implicit val defaultTimeout: Duration = Duration(5, "seconds")

  def await[A](future: Future[A])(implicit timeout: Duration): A = Await.result(future, timeout)
}
