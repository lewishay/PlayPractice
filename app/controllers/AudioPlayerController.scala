package controllers

import java.nio.file.Paths
import javax.inject.Inject
import play.api.libs.Files.TemporaryFile
import play.api.mvc.{Action, AnyContent, ControllerComponents, MultipartFormData}
import views.html.AudioPlayerView
import views.html.errors.GenericErrorView

class AudioPlayerController @Inject()(audioPlayerView: AudioPlayerView,
                                      genericErrorView: GenericErrorView)(
                                      implicit cc: ControllerComponents) extends FrontendController {

  def show: Action[AnyContent] = Action { implicit request =>
    Ok(audioPlayerView())
  }

  def upload: Action[MultipartFormData[TemporaryFile]] = Action(parse.multipartFormData) { implicit request =>
    request.body.file("audio").map { audio =>
      val filename = Paths.get(audio.filename).getFileName
      if(filename.toString.endsWith(".mp3")) {
        val file = audio.ref.moveTo(Paths.get(s"public/temp/$filename"), replace = true)
        val path = file.toAbsolutePath.getFileName.toString
        Ok(audioPlayerView(filePath = Some(path)))
      } else {
        logger.warn("[AudioPlayerController][upload] - An attempt to upload a non-mp3 file was made")
        BadRequest(audioPlayerView(formError = true))
      }
    }.getOrElse {
      logger.warn("[AudioPlayerController][upload] - The 'audio' file key was not found")
      InternalServerError(genericErrorView())
    }
  }
}
