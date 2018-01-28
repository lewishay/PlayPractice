package controllers

import javax.inject.Inject

import common.Common
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class PixelGridController  @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def defaultGrid: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.pixelGrid(Common.gridList, "Checked grid"))
  }

  def loadGrid(gridName: String): Action[AnyContent] = Action { implicit request =>
    Ok(views.html.pixelGrid(Common.gridList, gridName))
  }
}
