package views

import models.CommitLog
import models.viewModels.GithubViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class GithubCommitsViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    def repoTitle(column: Int) = s"body > section > div > div > div:nth-child($column) > h2"
    def firstCommitInfo(column: Int) =
      s"body > section > div > div > div:nth-child($column) > ol > li:nth-child(1) > p:nth-child(1)"
    def firstCommitMessage(column: Int) =
      s"body > section > div > div > div:nth-child($column) > ol > li:nth-child(1) > p:nth-child(2)"
    def errorMessage(column: Int) = s"body > section > div > div > div:nth-child($column)"
  }

  "The GithubCommits page" when {

    "valid commit logs are retrieved" should {

      val commitLog1 =
        CommitLog("repo-one", "master", List(("image.png", "myUsername", "2018-01-01, 12:00", "Fixed broken tests")))
      val commitLog2 =
        CommitLog("repo-two", "master", List(("image.png", "coolGuy5000", "2018-02-02, 13:00", "Broke all the tests")))
      val commitLog3 =
        CommitLog("repo-three", "master", List(("image.png", "importantDev", "2018-03-03, 14:00", "Refactored the bad code")))
      val commitLog4 =
        CommitLog("repo-four", "master", List(("image.png", "noIdea", "2018-04-04, 15:00", "Pushed straight to master LOL")))

      val viewModel = GithubViewModel(Some(commitLog1), Some(commitLog2), Some(commitLog3), Some(commitLog4))
      lazy val view = views.html.githubCommits(viewModel)
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Github Commits"
      }

      "have the correct repo titles" in {
        val titles = Array("repo-one", "repo-two", "repo-three", "repo-four")
        for(i <- 1 to 4) {
          elementText(Selectors.repoTitle(i)) shouldBe titles(i - 1)
        }
      }

      "have the correct user and date information" in {
        val commitInfo = Array(
          "myUsername 2018-01-01, 12:00",
          "coolGuy5000 2018-02-02, 13:00",
          "importantDev 2018-03-03, 14:00",
          "noIdea 2018-04-04, 15:00"
        )
        for(i <- 1 to 4) {
          elementText(Selectors.firstCommitInfo(i)) shouldBe commitInfo(i - 1)
        }
      }

      "have the correct commit messages" in {
        val commits =
          Array("Fixed broken tests", "Broke all the tests", "Refactored the bad code", "Pushed straight to master LOL")
        for(i <- 1 to 4) {
          elementText(Selectors.firstCommitMessage(i)) shouldBe commits(i - 1)
        }
      }
    }

    "no valid commit logs are retrieved" should {

      val viewModel = GithubViewModel(None, None, None, None)
      lazy val view = views.html.githubCommits(viewModel)
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Github Commits"
      }

      "display error messages in place of commit logs" in {
        for(i <- 1 to 4) {
          elementText(Selectors.errorMessage(i)) shouldBe "There was a problem retrieving this commit log."
        }
      }
    }
  }
}
