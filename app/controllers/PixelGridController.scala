package controllers

import javax.inject.Inject

import common.Grids._
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class PixelGridController  @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def blankGrid: Action[AnyContent] = Action {
    Ok(views.html.pixelGrid(checkedGrid))
  }
}
