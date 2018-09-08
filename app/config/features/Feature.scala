package config.features

import play.api.Configuration

class Feature(val key: String, config: Configuration) {

  def apply(value: Boolean): Unit = sys.props += key -> value.toString

  def apply(): Boolean = sys.props.get(key).fold(config.getOptional[Boolean](key).getOrElse(false))(_.toBoolean)
}
