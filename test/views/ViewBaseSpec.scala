package views

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.scalatest.{Matchers, OptionValues, WordSpecLike}

import scala.collection.JavaConverters._

trait ViewBaseSpec extends WordSpecLike with Matchers with OptionValues {

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
