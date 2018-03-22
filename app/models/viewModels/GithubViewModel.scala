package models.viewModels

case class GithubViewModel(repo1: Option[List[(String, String, String)]],
                           repo2: Option[List[(String, String, String)]],
                           repo3: Option[List[(String, String, String)]],
                           repo4: Option[List[(String, String, String)]])
