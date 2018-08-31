package forms

import config.ConfigKeys
import models.FeatureSwitchModel
import play.api.data.Form
import play.api.data.Forms._

object FeatureSwitchForm {

  val form: Form[FeatureSwitchModel] = Form(
    mapping(
      ConfigKeys.infoBanner -> boolean
    )(FeatureSwitchModel.apply)(FeatureSwitchModel.unapply)
  )
}
