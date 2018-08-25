package views

import config.AppConfig
import mocks.MockAppConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.i18n._
import play.api.inject.Injector

import scala.collection.JavaConverters._

trait ViewBaseSpec extends WordSpecLike with Matchers with OptionValues with I18nSupport with GuiceOneAppPerSuite {

  lazy val injector: Injector = app.injector
  lazy val messagesApi: MessagesApi = injector.instanceOf[MessagesApi]
  implicit lazy val messages: Messages = MessagesImpl(Lang("en-GB"), messagesApi)
  implicit val mockAppConfig: AppConfig = new MockAppConfig(app.configuration)

  def elementText(cssSelector: String)(implicit document: Document): String = {
    element(cssSelector).text()
  }

  def elementAttributes(cssSelector: String)(implicit document: Document): Map[String, String] = {
    val attributes = element(cssSelector).attributes.asList().asScala.toList
    attributes.map(attribute => (attribute.getKey, attribute.getValue)).toMap
  }

  def element(cssSelector: String)(implicit document: Document): Element = {
    val elements = document.select(cssSelector)

    if(elements.size == 0) {
      fail(s"No element exists with the selector '$cssSelector'")
    }

    document.select(cssSelector).first()
  }

  def formatHtml(markup: String): String = Jsoup.parseBodyFragment(s"\n$markup\n").toString.trim
}
