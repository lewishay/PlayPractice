package config

import javax.inject.{Inject, Singleton}

import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result}
import play.api.mvc.Results.{BadRequest, InternalServerError, NotFound}
import play.mvc.Http.Status.NOT_FOUND
import views.html.errors.{GenericErrorView, NotFoundView}

import scala.concurrent.Future

@Singleton
class ErrorHandler @Inject()(genericErrorView: GenericErrorView, notFoundView: NotFoundView) extends HttpErrorHandler {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] =
    statusCode match {
      case NOT_FOUND => Future.successful(NotFound(notFoundView()))
      case _ => Future.successful(BadRequest(genericErrorView()))
    }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] =
    Future.successful(InternalServerError(genericErrorView()))
}
