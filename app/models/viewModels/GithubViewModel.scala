package models.viewModels

import models.CommitLog

case class GithubViewModel(commitLog1: Option[CommitLog],
                           commitLog2: Option[CommitLog],
                           commitLog3: Option[CommitLog],
                           commitLog4: Option[CommitLog])
