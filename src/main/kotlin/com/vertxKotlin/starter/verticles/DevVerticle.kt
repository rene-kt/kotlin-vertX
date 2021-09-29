package com.vertxKotlin.starter.verticles

import com.vertxKotlin.starter.exceptions.ObjectNotFoundException
import com.vertxKotlin.starter.exceptions.UserNotLoggedException
import com.vertxKotlin.starter.handlers.ExceptionsResponseHandler
import com.vertxKotlin.starter.handlers.ResponseHandler
import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.Project
import com.vertxKotlin.starter.services.DevService
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class DevVerticle: AbstractVerticle() {

  private val router: Router = Router.router(vertx)
  private val exceptionsResponseHandler = ExceptionsResponseHandler()
  private val devService = DevService()

  fun returnDevUser(devLogged: DevUser, req: RoutingContext ){
    if (devLogged.id == 0) exceptionsResponseHandler.userNotLoggedExceptionResponse(req, UserNotLoggedException())

    req.response().setStatusCode(200).putHeader("content-type", "application/json")
      .end(Json.encodePrettily(ResponseHandler(200, "Successful search", JsonObject.mapFrom(devLogged))))
  }

  fun createDevUser(req: RoutingContext): DevUser {
    var devLogged = DevUser()
    try {
      devLogged = devService.createDevUser(req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(devLogged))))
        return devLogged
    } catch (e: NullPointerException) {
      exceptionsResponseHandler.nullRequestBodyResponse(req, e)
    }
    return devLogged
  }

  fun createProject(devLogged: DevUser, req: RoutingContext){
    var project = Project()

    try {
      devService.createProject(devLogged, project.jsonToObject(req.bodyAsJson))
      req.response().putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "Your project was created!", JsonObject.mapFrom(devLogged))))
    } catch (e: NullPointerException) {
      exceptionsResponseHandler.nullRequestBodyResponse(req, e)
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    }
  }

  fun deleteProject(devLogged: DevUser, req: RoutingContext){

    val projectId: Int = req.request().getParam("projectId").toInt()

    try {
      devService.deleteProject(devLogged, projectId)
      req.response().putHeader("content-type", "application/json").setStatusCode(204).end()
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    } catch (e: ObjectNotFoundException) {
      exceptionsResponseHandler.objectNotFoundExceptionResponse(req, e)
    }
  }
}
