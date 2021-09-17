package config

import com.google.inject.AbstractModule

class DIModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[AppConfig]).to(classOf[FrontendAppConfig]).asEagerSingleton()
  }
}
