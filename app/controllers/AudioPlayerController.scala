/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import java.nio.file.Paths
import javax.inject.Inject

import config.AppConfig
import play.api.Logger
import play.api.libs.Files.TemporaryFile
import play.api.mvc.{Action, AnyContent, ControllerComponents, MultipartFormData}

class AudioPlayerController @Inject()(implicit cc: ControllerComponents,
                                      implicit val appConfig: AppConfig) extends FrontendController {

  def show: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.audioPlayer())
  }

  def upload: Action[MultipartFormData[TemporaryFile]] = Action(parse.multipartFormData) { implicit request =>
    request.body.file("audio").map { audio =>
      val filename = Paths.get(audio.filename).getFileName
      if(filename.toString.endsWith(".mp3")) {
        val file = audio.ref.moveTo(Paths.get(s"public/temp/$filename"), replace = true)
        val path = file.path.getFileName.toString
        Ok(views.html.audioPlayer(filePath = Some(path)))
      } else {
        Logger.warn("[AudioPlayerController][upload] - An attempt to upload a non-mp3 file was made")
        BadRequest(views.html.audioPlayer(formError = true))
      }
    }.getOrElse {
      Logger.warn("[AudioPlayerController][upload] - The 'audio' file key was not found")
      InternalServerError(views.html.errors.genericError())
    }
  }
}
