package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.html.HomeView

class HomeViewSpec extends ViewBaseSpec {

  val injectedView: HomeView = injector.instanceOf[HomeView]

  object Selectors {
    val title = "h1"
    val home = "#mainNav > div > a"
    val admin = "#navbarResponsive > ul > li > a"
    def boxElement(row: Int, col: Int): String =
      s"div:nth-child($row) > div:nth-child($col) > a > div > div.col-md-8.side-text"
    def elementGrab(num: Int, getTitles: Boolean): String = (num % 2 == 0, getTitles) match {
      case (true, true) => boxElement(num / 2, 2) + " > strong"
      case (false, true) => boxElement((num / 2) + 1, 1) + " > strong"
      case (true, false) => boxElement(num / 2, 2) + " > p"
      case (false, false) => boxElement((num / 2) + 1, 1) + " > p"
    }
    def footerTitle(col: Int): String = s"footer > div.footer-above > div > div > div:nth-child($col) > h3"
    def footerInfo(col: Int): String = s"footer > div.footer-above > div > div > div:nth-child($col) > p"
    val copyright = "body > footer > div.footer-below > div > div > div"
    val infoBanner = ".info-banner"
    def footerLink(num: Int): String = s"li.list-inline-item:nth-child($num) > a"
  }

  "The Home page" when {

    "no features are enabled" should {

      mockAppConfig.features.infoBannerEnabled(false)
      lazy implicit val document: Document = Jsoup.parse(injectedView().body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Home"
      }

      "have the home link in the header" in {
        elementText(Selectors.home) shouldBe "Home"
      }

      "have the admin button in the header" in {
        elementText(Selectors.admin) shouldBe "Admin"
      }

      "have the correct titles for the other pages on the website" in {
        val titles = Vector(
          "Audio player",
          "Hangman",
          "WoW boss API",
          "Github commit log",
          "Pixel grid",
          "Word square"
        )
        titles.indices.foreach(i => titles(i) shouldBe elementText(Selectors.elementGrab(i + 1, getTitles = true)))
      }

      "have the correct descriptions for the other pages on the website" in {
        val descriptions = Vector(
          "Upload and play an audio file",
          "Play a game of hangman",
          "View boss data from the Battle.net API",
          "View a list of recent Github commits",
          "Generate images from a HTML table",
          "Generate a custom word square"
        )
        descriptions.indices.foreach(i => descriptions(i) shouldBe elementText(Selectors.elementGrab(i + 1, getTitles = false)))
      }

      "have the correct 'location' information in the footer" in {
        elementText(Selectors.footerTitle(1)) shouldBe "Location"
        elementText(Selectors.footerInfo(1)) shouldBe "Telford Plaza, Ironmasters Way Telford, TF3 4NB"
      }

      "have the correct 'around the web' information in the footer" in {
        elementText(Selectors.footerTitle(2)) shouldBe "Around the web"
        element(Selectors.footerLink(1)).attr("href") shouldBe "https://www.github.com/lewishay"
        element(Selectors.footerLink(2)).attr("href") shouldBe "https://www.youtube.com/lewishay"
        element(Selectors.footerLink(3)).attr("href") shouldBe "https://www.linkedin.com/in/lewis-hay"
        elementText(Selectors.copyright) shouldBe "Copyright © 2017-2021 don't steal pls"
      }

      "have the correct 'about' information in the footer" in {
        elementText(Selectors.footerTitle(3)) shouldBe "About the website"
        elementText(Selectors.footerInfo(3)) shouldBe
          "This website is part of a practice exercise to explore the Play Framework using Scala."
      }
    }
  }

  "the info banner feature is enabled" should {

    mockAppConfig.features.infoBannerEnabled(true)
    lazy implicit val document: Document = Jsoup.parse(injectedView().body)

    "have the info banner" in {
      elementText(Selectors.infoBanner) shouldBe
        "NOTICE: This website is currently undergoing development. There may be brief outages as new updates are applied."
    }
  }
}
