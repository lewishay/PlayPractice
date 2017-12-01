package views

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.i18n.Messages
import play.api.inject.Injector

import scala.collection.JavaConverters._

trait ViewBaseSpec extends WordSpecLike with Matchers with OptionValues with GuiceOneAppPerSuite {

  lazy val injector: Injector = app.injector
  lazy implicit val messages: Messages = injector.instanceOf[Messages]

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
