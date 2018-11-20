package controllers

import javax.inject.Inject

import common.Common
import config.AppConfig
import play.api.mvc.{Action, AnyContent, ControllerComponents}

class PixelGridController @Inject()(implicit cc: ControllerComponents,
                                    implicit val appConfig: AppConfig) extends FrontendController {

  def defaultGrid: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.pixelGrid(Common.gridList, "Checked grid"))
  }

  def loadGrid(gridName: String): Action[AnyContent] = Action { implicit request =>
    Ok(views.html.pixelGrid(Common.gridList, gridName))
  }
}
