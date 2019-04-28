package controllers

import javax.inject.Inject

import play.api.mvc.{Action, AnyContent, ControllerComponents}
import views.html.PixelGridView

class PixelGridController @Inject()(pixelGridView: PixelGridView)(
                                    implicit cc: ControllerComponents) extends FrontendController {

  def defaultGrid: Action[AnyContent] = Action { implicit request =>
    Ok(pixelGridView("Checked grid"))
  }

  def loadGrid(gridName: String): Action[AnyContent] = Action { implicit request =>
    Ok(pixelGridView(gridName))
  }
}
