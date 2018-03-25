package models

case class CommitLog(repo: String, branch: String, commits: List[(String, String, String, String)])
