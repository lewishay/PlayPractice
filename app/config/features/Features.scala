package config.features

import javax.inject.{Inject, Singleton}

import config.ConfigKeys
import play.api.Configuration

@Singleton
class Features @Inject()(config: Configuration) {

  val infoBannerEnabled = new Feature(ConfigKeys.infoBanner, config)
}
